package com.drinks.demo.database;

import com.drinks.demo.model.Order;
import com.drinks.demo.model.OrderDetail;
import com.drinks.demo.utilities.DBConnection;

import java.io.IOException;
import java.nio.file.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderDao {
    private static final String REPORT_DIR = "reports/";

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

    public static class BranchSales {
        public final String branchName;
        public final double totalSales;

        public BranchSales(String branchName, double totalSales) {
            this.branchName = branchName;
            this.totalSales = totalSales;
        }
    }

    public static class TopDrink {
        public final String drinkName;
        public final int totalOrders;
        public final double totalRevenue;

        public TopDrink(String drinkName, int totalOrders, double totalRevenue) {
            this.drinkName = drinkName;
            this.totalOrders = totalOrders;
            this.totalRevenue = totalRevenue;
        }
    }

    public static class RecentOrder {
        public final int orderId;
        public final Timestamp orderDate;
        public final String customerName;
        public final double totalAmount;

        public RecentOrder(int orderId, Timestamp orderDate, String customerName, double totalAmount) {
            this.orderId = orderId;
            this.orderDate = orderDate;
            this.customerName = customerName;
            this.totalAmount = totalAmount;
        }
    }

    public String generateFullReport() throws SQLException, IOException {
        Path reportDir = Paths.get(REPORT_DIR);
        if (!Files.exists(reportDir)) {
            Files.createDirectories(reportDir);
        }

        String timestamp = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String filename = "sales_report_" + timestamp + ".txt";
        Path reportPath = reportDir.resolve(filename);

        double totalSales = getTotalSales();
        List<BranchSales> branchSalesList = getBranchSalesSummary();
        List<TopDrink> topDrinks = getTopSellingDrinks(5);
        List<RecentOrder> recentOrders = getRecentOrders(10);

        StringBuilder report = new StringBuilder();
        report.append("=== DRINK MANAGEMENT SYSTEM - SALES REPORT ===\n")
                .append("Generated on: ").append(LocalDate.now()).append("\n\n")
                .append("=== SUMMARY ===\n")
                .append(String.format("Total Sales: KES %,.2f\n\n", totalSales))
                .append("=== BRANCH PERFORMANCE ===\n");

        for (BranchSales branch : branchSalesList) {
            double percentage = totalSales > 0 ? (branch.totalSales / totalSales * 100) : 0;
            report.append(String.format("%-20s: KES %,.2f (%.1f%% of total)\n", branch.branchName, branch.totalSales, percentage));
        }

        report.append("\n=== TOP SELLING DRINKS ===\n");
        for (TopDrink drink : topDrinks) {
            report.append(String.format("%-20s: %d orders (KES %,.2f revenue)\n", drink.drinkName, drink.totalOrders, drink.totalRevenue));
        }

        report.append("\n=== RECENT ORDERS ===\n");
        for (RecentOrder order : recentOrders) {
            report.append(String.format("#%d - %s - %s - KES %,.2f\n", order.orderId, order.orderDate.toLocalDateTime().toLocalDate(), order.customerName, order.totalAmount));
        }

        Files.write(reportPath, report.toString().getBytes());
        return reportPath.toString();
    }

    public double getTotalSales() throws SQLException {
        String sql = "SELECT COALESCE(SUM(total_amount), 0) AS total FROM orders";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next() ? rs.getDouble("total") : 0;
        }
    }

    private List<BranchSales> getBranchSalesSummary() throws SQLException {
        List<BranchSales> summary = new ArrayList<>();
        String sql = "SELECT b.name AS branch_name, SUM(o.total_amount) AS total_sales FROM orders o JOIN branches b ON o.branch_id = b.branch_id GROUP BY b.name ORDER BY total_sales DESC";

        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                summary.add(new BranchSales(rs.getString("branch_name"), rs.getDouble("total_sales")));
            }
        }
        return summary;
    }

    private List<TopDrink> getTopSellingDrinks(int limit) throws SQLException {
        List<TopDrink> topDrinks = new ArrayList<>();
        String sql = "SELECT d.name AS drink_name, SUM(od.quantity) AS total_orders, SUM(od.quantity * od.price) AS total_revenue FROM order_details od JOIN drinks d ON od.drink_id = d.drink_id GROUP BY d.name ORDER BY total_revenue DESC LIMIT ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    topDrinks.add(new TopDrink(rs.getString("drink_name"), rs.getInt("total_orders"), rs.getDouble("total_revenue")));
                }
            }
        }
        return topDrinks;
    }

    private List<RecentOrder> getRecentOrders(int limit) throws SQLException {
        List<RecentOrder> recentOrders = new ArrayList<>();
        String sql = "SELECT o.order_id, o.order_date, u.name AS customer_name, o.total_amount FROM orders o JOIN users u ON o.user_id = u.user_id ORDER BY o.order_date DESC LIMIT ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    recentOrders.add(new RecentOrder(rs.getInt("order_id"), rs.getTimestamp("order_date"), rs.getString("customer_name"), rs.getDouble("total_amount")));
                }
            }
        }
        return recentOrders;
    }

    public List<Order> getOrdersByBranchId(int branchId) {
        List<Order> orders = new ArrayList<>();
        String sql = """
        SELECT o.order_id, o.user_id, o.branch_id, o.order_date, o.total_amount,
               u.name AS customer_name, b.name AS branch_name
        FROM orders o
        JOIN users u ON o.user_id = u.user_id
        JOIN branches b ON o.branch_id = b.branch_id
        WHERE o.branch_id = ?
        ORDER BY o.order_date DESC
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, branchId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order(
                            rs.getInt("order_id"),
                            rs.getInt("user_id"),
                            rs.getInt("branch_id"),
                            rs.getTimestamp("order_date"),
                            rs.getDouble("total_amount")
                    );
                    order.setCustomerName(rs.getString("customer_name"));
                    order.setBranchName(rs.getString("branch_name"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
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


    public int addOrder(Order order) {
        String sql = "INSERT INTO orders (user_id, branch_id, order_date, total_amount) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        String sql = "SELECT o.order_id, o.user_id, o.branch_id, o.order_date, o.total_amount FROM orders o WHERE o.order_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Order(rs.getInt("order_id"), rs.getInt("user_id"), rs.getInt("branch_id"), rs.getTimestamp("order_date"), rs.getDouble("total_amount"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

        // Parameters list for filtering
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
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            // Bind parameters
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
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

}
