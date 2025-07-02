package com.drinks.demo.database;

import com.drinks.demo.utilities.DBConnection;

import java.sql.*;

public class AdminDAO {
    public boolean validateAdmin(String username, String password) {
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // true if admin found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
