package com.drinks.demo.database;

import com.drinks.demo.model.Customer;
import com.drinks.demo.utilities.DBConnection;

import java.sql.*;

public class CustomerDao {
    public int addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (name, contact) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getContact());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Return generated customer_id
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Error
    }
    public String getCustomerNameById(int customerId) {
        String sql = "SELECT name FROM customers WHERE customer_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
