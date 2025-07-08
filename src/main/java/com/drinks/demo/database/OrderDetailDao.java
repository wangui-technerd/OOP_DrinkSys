package com.drinks.demo.database;

import com.drinks.demo.model.OrderDetail;
import com.drinks.demo.utilities.DBConnection;

import java.sql.*;

public class OrderDetailDao {
    public void addOrderDetaildrinks(OrderDetail orderDetail) {
        String sql = "INSERT INTO order_details (order_id, drink_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderDetail.getOrderId());
            pstmt.setInt(2, orderDetail.getDrinkId());
            pstmt.setInt(3, orderDetail.getQuantity());
            pstmt.setDouble(4, orderDetail.getPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOrderDetail(OrderDetail detail) {
    }
}