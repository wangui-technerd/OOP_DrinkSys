package com.drinks.demo.database;

import com.drinks.demo.model.Order;
import com.drinks.demo.model.OrderDetail;
import com.drinks.demo.utilities.DBConnection;

import java.io.IOException;
import java.nio.file.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    public List<Order> getOrdersByCustomer(int customerId) {
        List<Order> orders = new ArrayList<>();
        String sql = """
            SELECT o.order_id, o.user_id, o.branch_id, o.order_date, o.total_amount
            FROM orders o
            WHERE o.user_id = ?
            ORDER BY o.order_date DESC
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(new Order(
                            rs.getInt("order_id"),
                            rs.getInt("user_id"),
                            rs.getInt("branch_id"),
                            rs.getTimestamp("order_date"),
                            rs.getDouble("total_amount")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public List<Order> getOrdersByBranchId(int branchId) {
        List<Order> orders = new ArrayList<>();
        String sql = """
            SELECT o.order_id, u.name AS customer_name, b.name AS branch_name,
                   d.name AS drink_name, od.quantity, od.price
            FROM orders o
            JOIN users u ON o.user_id = u.user_id
            JOIN branches b ON o.branch_id = b.branch_id
            JOIN order_details od ON o.order_id = od.order_id
            JOIN drinks d ON od.drink_id = d.drink_id
            WHERE o.branch_id = ?
            ORDER BY o.order_date DESC
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, branchId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setCustomerName(rs.getString("customer_name"));
                    order.setBranchName(rs.getString("branch_name"));
                    order.setDrinkName(rs.getString("drink_name"));
                    order.setQuantity(rs.getInt("quantity"));
                    order.setPrice(rs.getDouble("price"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public int addOrder(Order order) {
        String sql = "INSERT INTO orders (user_id, branch_id, order_date, total_amount) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, order.getCustomerId());
            pstmt.setInt(2, order.getBranchId());
            pstmt.setTimestamp(3, order.getOrderDate());
            pstmt.setDouble(4, order.getTotalAmount());

            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void addOrderDetail(OrderDetail detail) {
        String sql = "INSERT INTO order_details (order_id, drink_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, detail.getOrderId());
            pstmt.setInt(2, detail.getDrinkId());
            pstmt.setInt(3, detail.getQuantity());
            pstmt.setDouble(4, detail.getPrice());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Order getOrderById(int orderId) {
        String sql = """
            SELECT o.order_id, o.user_id, o.branch_id, o.order_date, o.total_amount,
                   u.name AS customer_name, b.name AS branch_name
            FROM orders o
            JOIN users u ON o.user_id = u.user_id
            JOIN branches b ON o.branch_id = b.branch_id
            WHERE o.order_id = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order(
                            rs.getInt("order_id"),
                            rs.getInt("user_id"),
                            rs.getInt("branch_id"),
                            rs.getTimestamp("order_date"),
                            rs.getDouble("total_amount")
                    );
                    order.setCustomerName(rs.getString("customer_name"));
                    order.setBranchName(rs.getString("branch_name"));
                    return order;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public double getTotalSales() throws SQLException {
        String sql = "SELECT COALESCE(SUM(total_amount), 0) AS total FROM orders";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.next() ? rs.getDouble("total") : 0;
        }
    }

    public double getBranchSales(int branchId) {
        String sql = "SELECT SUM(total_amount) AS branch_sales FROM orders WHERE branch_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, branchId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("branch_sales");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    public List<Order> getOrdersFiltered(String branch, String drinkType) {
        List<Order> orders = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
            SELECT o.order_id, o.order_date, b.name AS branch_name,
                   d.name AS drink_name, od.quantity, (od.price * od.quantity) AS amount
            FROM orders o
            JOIN order_details od ON o.order_id = od.order_id
            JOIN drinks d ON od.drink_id = d.drink_id
            JOIN branches b ON o.branch_id = b.branch_id
            WHERE 1 = 1
        """);

        List<Object> params = new ArrayList<>();

        if (branch != null && !branch.isBlank()) {
            sql.append(" AND b.name = ?");
            params.add(branch);
        }

        if (drinkType != null && !drinkType.isBlank()) {
            sql.append(" AND d.name = ?");
            params.add(drinkType);
        }

        sql.append(" ORDER BY o.order_date DESC");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order(
                            rs.getString("branch_name"),
                            rs.getString("drink_name"),
                            rs.getTimestamp("order_date"),
                            rs.getInt("quantity"),
                            rs.getDouble("amount")
                    );
                    orders.add(order);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public String generateFullReport() throws IOException, SQLException {
        List<Order> allOrders = getOrdersFiltered(null, null);
        StringBuilder content = new StringBuilder("Order ID, Branch, Drink, Date, Quantity, Amount\n");

        for (Order o : allOrders) {
            content.append(o.getOrderId()).append(",")
                    .append(o.getBranch()).append(",")
                    .append(o.getDrink()).append(",")
                    .append(o.getOrderDate().toLocalDateTime().toLocalDate()).append(",")
                    .append(o.getQuantity()).append(",")
                    .append(o.getAmount()).append("\n");
        }

        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path reportDir = Paths.get("reports");
        if (!Files.exists(reportDir)) Files.createDirectories(reportDir);

        Path filePath = reportDir.resolve("sales_report_" + dateStr + ".csv");
        Files.writeString(filePath, content.toString(), StandardOpenOption.CREATE);

        return filePath.toAbsolutePath().toString();
    }
}
