package com.drinks.demo.database;

import com.drinks.demo.utilities.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentDao {

    public boolean save(int orderId, String method, String transactionCode) {
        String sql = "INSERT INTO payments (order_id, method, transaction_code) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            stmt.setString(2, method);
            stmt.setString(3, transactionCode);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
