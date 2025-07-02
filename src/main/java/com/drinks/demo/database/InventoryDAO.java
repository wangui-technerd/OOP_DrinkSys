package com.drinks.demo.database;

import com.drinks.demo.model.Inventory;
import com.drinks.demo.utilities.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
    public List<Inventory> getLowStock(int branchId) {
        List<Inventory> lowStockList = new ArrayList<>();
        String sql = "SELECT * FROM inventory WHERE branch_id = ? AND quantity < min_threshold";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, branchId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Inventory inv = new Inventory(
                            rs.getInt("inventory_id"),
                            rs.getInt("branch_id"),
                            rs.getInt("drink_id"),
                            rs.getInt("quantity"),
                            rs.getInt("min_threshold")
                    );
                    lowStockList.add(inv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lowStockList;
    }
}
