package com.drinks.demo.database;

import com.drinks.demo.model.Order;
import com.drinks.demo.model.OrderDetail;
import com.drinks.demo.utilities.DBConnection;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private static final String REPORT_DIR = "reports/";

    // Records for report data structure
    private static class BranchSales {
        private final String branchName;
        private final double totalSales;

        public BranchSales(String branchName, double totalSales) {
            this.branchName = branchName;
            this.totalSales = totalSales;
        }

        public String branchName() { return branchName; }
        public double totalSales() { return totalSales; }
    }

    private static class TopDrink {
        private final String drinkName;
        private final int totalOrders;
        private final double totalRevenue;

        public TopDrink(String drinkName, int totalOrders, double totalRevenue) {
            this.drinkName = drinkName;
            this.totalOrders = totalOrders;
            this.totalRevenue = totalRevenue;
        }

        public String drinkName() { return drinkName; }
        public int totalOrders() { return totalOrders; }
        public double totalRevenue() { return totalRevenue; }
    }

    private static class RecentOrder {
        private final int orderId;
        private final Timestamp orderDate;
        private final String customerName;
        private final double totalAmount;

        public RecentOrder(int orderId, Timestamp orderDate, String customerName, double totalAmount) {
            this.orderId = orderId;
            this.orderDate = orderDate;
            this.customerName = customerName;
            this.totalAmount = totalAmount;
        }

        public int orderId() { return orderId; }
        public Timestamp orderDate() { return orderDate; }
        public String customerName() { return customerName; }
        public double totalAmount() { return totalAmount; }
    }

    public String generateFullReport() throws SQLException, IOException {
        // Create report directory if it doesn't exist
        Path reportDir = Paths.get(REPORT_DIR);
        if (!Files.exists(reportDir)) {
            Files.createDirectories(reportDir);
        }

        // Generate filename with timestamp
        String timestamp = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String filename = "sales_report_" + timestamp + ".txt";
        Path reportPath = reportDir.resolve(filename);

        // Collect all data needed for the report
        double totalSales = getTotalSales();
        List<BranchSales> branchSalesList = getBranchSalesSummary();
        List<TopDrink> topDrinks = getTopSellingDrinks(5);
        List<RecentOrder> recentOrders = getRecentOrders(10);



        // Generate report content
        StringBuilder report = new StringBuilder();
        report.append("=== DRINK MANAGEMENT SYSTEM - SALES REPORT ===\n");
        report.append("Generated on: ").append(LocalDate.now()).append("\n\n");

        // 1. Summary Section
        report.append("=== SUMMARY ===\n");
        report.append(String.format("Total Sales: KES %,.2f\n\n", totalSales));

        // 2. Branch Performance
        report.append("=== BRANCH PERFORMANCE ===\n");
        for (BranchSales branch : branchSalesList) {
            double percentage = totalSales > 0 ?
                    (branch.totalSales() / totalSales * 100) : 0;

            report.append(String.format("%-20s: KES %,.2f (%.1f%% of total)\n",
                    branch.branchName(),
                    branch.totalSales(),
                    percentage));
        }



        report.append("\n");

        // 3. Top Selling Products
        report.append("=== TOP SELLING DRINKS ===\n");
        for (TopDrink drink : topDrinks) {
            report.append(String.format("%-20s: %d orders (KES %,.2f revenue)\n",
                    drink.drinkName(),
                    drink.totalOrders(),
                    drink.totalRevenue()));
        }
        report.append("\n");

        // 4. Recent Orders
        report.append("=== RECENT ORDERS ===\n");
        for (RecentOrder order : recentOrders) {
            report.append(String.format("#%d - %s - %s - KES %,.2f\n",
                    order.orderId(),
                    order.orderDate().toLocalDateTime().toLocalDate(),
                    order.customerName(),
                    order.totalAmount()));
        }

        // Write to file
        Files.write(reportPath, report.toString().getBytes());
        return reportPath.toString();
    }

    public double getTotalSales() throws SQLException{
        String sql = "SELECT COALESCE(SUM(total_amount), 0) AS total FROM orders";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next() ? rs.getDouble("total") : 0;
        }
    }



    // Method to get sales summary by branch
    private List<BranchSales> getBranchSalesSummary() throws SQLException {
        List<BranchSales> summary = new ArrayList<>();
        String sql = "SELECT b.name AS branch_name, SUM(o.total_amount) AS total_sales " +
                "FROM orders o " +
                "JOIN branches b ON o.branch_id = b.branch_id " +
                "GROUP BY b.name " +
                "ORDER BY total_sales DESC";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                summary.add(new BranchSales(
                        rs.getString("branch_name"),
                        rs.getDouble("total_sales")
                ));
            }
        }
        return summary;
    }

    private List<TopDrink> getTopSellingDrinks(int limit) throws SQLException {
        List<TopDrink> topDrinks = new ArrayList<>();
        String sql = "SELECT d.name AS drink_name, " +
                "SUM(od.quantity) AS total_orders, " +
                "SUM(od.quantity * od.price) AS total_revenue " +
                "FROM order_details od " +
                "JOIN drinks d ON od.drink_id = d.drink_id " +
                "GROUP BY d.name " +
                "ORDER BY total_revenue DESC " +
                "LIMIT ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    topDrinks.add(new TopDrink(
                            rs.getString("drink_name"),
                            rs.getInt("total_orders"),
                            rs.getDouble("total_revenue")
                    ));
                }
            }
        }
        return topDrinks;
    }

    // Method to get recent orders
    private List<RecentOrder> getRecentOrders(int limit) throws SQLException {
        List<RecentOrder> recentOrders = new ArrayList<>();
        String sql = "SELECT o.order_id, o.order_date, u.name AS customer_name, o.total_amount " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.user_id " +
                "ORDER BY o.order_date DESC " +
                "LIMIT ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    recentOrders.add(new RecentOrder(
                            rs.getInt("order_id"),
                            rs.getTimestamp("order_date"),
                            rs.getString("customer_name"),
                            rs.getDouble("total_amount")
                    ));
                }
            }
        }
        return recentOrders;
    }

    public int addOrder(Order order) {
        String sql = "INSERT INTO orders (user_id, branch_id, order_date, total_amount) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, order.getCustomerId());
            pstmt.setInt(2, order.getBranchId());
            pstmt.setTimestamp(3, order.getOrderDate());
            pstmt.setDouble(4, order.getTotalAmount());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Return generated order_id
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Error
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

    public List<Order> getOrdersByCustomer(int customerId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.order_id, o.order_date, o.total_amount, o.branch_id, " +
                "u.user_id AS customer_id, u.name AS customer_name, " +
                "d.name AS drink_name, od.quantity, od.price, b.name AS branch_name " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.user_id " +
                "JOIN order_details od ON o.order_id = od.order_id " +
                "JOIN drinks d ON od.drink_id = d.drink_id " +
                "JOIN branches b ON o.branch_id = b.branch_id " +
                "WHERE u.user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order(
                            rs.getInt("order_id"),
                            rs.getInt("customer_id"),
                            rs.getString("customer_name"),
                            rs.getString("drink_name"),
                            rs.getInt("branch_id"),
                            rs.getString("branch_name"),
                            rs.getInt("quantity"),
                            rs.getDouble("price"),
                            rs.getTimestamp("order_date"),
                            rs.getDouble("total_amount")
                    );
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> getOrdersByBranchId(int branchId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.order_id, o.order_date, o.total_amount, " +
                "u.user_id AS customer_id, u.name AS customer_name, " +
                "d.name AS drink_name, od.quantity, od.price, " +
                "b.name AS branch_name " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.user_id " +
                "JOIN order_details od ON o.order_id = od.order_id " +
                "JOIN drinks d ON od.drink_id = d.drink_id " +
                "JOIN branches b ON o.branch_id = b.branch_id " +
                "WHERE o.branch_id = ? " +
                "ORDER BY o.order_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, branchId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("order_id"),
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("drink_name"),
                        branchId,
                        rs.getString("branch_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getTimestamp("order_date"),
                        rs.getDouble("total_amount")
                );
                orders.add(order);
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

    public Order getOrderById(int orderId) {
        String sql = "SELECT o.order_id, o.user_id, u.name AS customer_name, " +
                "o.branch_id, b.name AS branch_name, o.order_date, o.total_amount " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.user_id " +
                "JOIN branches b ON o.branch_id = b.branch_id " +
                "WHERE o.order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Order(
                            rs.getInt("order_id"),
                            rs.getInt("user_id"),
                            rs.getString("customer_name"),
                            "", // drinkName not available here
                            rs.getInt("branch_id"),
                            rs.getString("branch_name"),
                            0,  // quantity not available here
                            0.0, // price not available here
                            rs.getTimestamp("order_date"),
                            rs.getDouble("total_amount")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
