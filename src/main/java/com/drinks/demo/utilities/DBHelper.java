package com.drinks.demo.utilities;

import com.drinks.demo.model.Branch;
import com.drinks.demo.model.Order;

import com.drinks.demo.model.Drink;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {
    public static List<Branch> getAllBranches() {
        List<Branch> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM branches")) {

            while (rs.next()) {
                Branch b = new Branch(rs.getInt("branch_id"), rs.getString("name"), rs.getString("location"));
                list.add(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

        String query = """
        SELECT o.id, u.id AS customerId, u.name AS customerName, d.name AS drinkName,
               b.id AS branchId, b.name AS branchName, od.quantity, d.price,
               o.order_date, od.total_price
        FROM orders o
        JOIN order_details od ON o.id = od.order_id
        JOIN users u ON o.customer_id = u.id
        JOIN drinks d ON od.drink_id = d.id
        JOIN branches b ON o.branch_id = b.id
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("id"),
                        rs.getInt("customerId"),
                        rs.getString("customerName"),
                        rs.getString("drinkName"),
                        rs.getInt("branchId"),
                        rs.getString("branchName"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getTimestamp("order_date"),
                        rs.getDouble("total_price")
                );
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
    public static List<Order> getCustomerOrders() {
        return getAllOrders();
    }

    public static List<Branch> getBranchSales() {
        List<Branch> branches = new ArrayList<>();

        String sql = """
                    SELECT b.branch_id AS id, b.name, b.location, SUM(od.total_price) AS totalSales
                                    FROM order_details od
                                    JOIN orders o ON od.order_id = o.id
                                    JOIN branches b ON o.branch_id = b.branch_id
                                    GROUP BY b.branch_id, b.name, b.location
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                branches.add(new Branch(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getDouble("totalSales")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return branches;
    }

    public static List<Drink> getLowStockItems(int threshold) {
        List<Drink> drinks = new ArrayList<>();
        String sql = "SELECT * FROM drinks WHERE quantity < ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, threshold);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                drinks.add(new Drink(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drinks;
    }

    public static double getTotalSales() {
        double total = 0.0;
        String sql = "SELECT SUM(total_price) AS total FROM order_details";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

}
