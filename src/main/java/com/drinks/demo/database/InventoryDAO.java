package com.drinks.demo.database;

import com.drinks.demo.model.Inventory;
import com.drinks.demo.utilities.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
    private int getBranchId(String branchName) throws SQLException {
        String sql = "SELECT branch_id FROM branches WHERE name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, branchName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("branch_id");
                }
            }
        }
        throw new SQLException("Branch not found: " + branchName);
    }

    public List<Inventory> getInventoryByBranch(String branchName) {
        List<Inventory> inventoryList = new ArrayList<>();
        String sql = "SELECT * FROM inventory WHERE branch_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, getBranchId(branchName));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Inventory inv = new Inventory(
                            rs.getInt("inventory_id"),
                            rs.getInt("branch_id"),
                            rs.getInt("drink_id"),
                            rs.getInt("quantity"),
                            rs.getInt("min_threshold")
                    );
                    inventoryList.add(inv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventoryList;
    }

    // Low stock items
    public List<Inventory> getLowStockItems(String branchName) {
        List<Inventory> lowStockList = new ArrayList<>();
        String sql = "SELECT * FROM inventory WHERE branch_id = ? AND quantity < min_threshold";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, getBranchId(branchName));
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

    public List<String> getAllDrinkNames() {
        List<String> drinks = new ArrayList<>();
        String sql = "SELECT DISTINCT drink_name FROM drinks";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                drinks.add(rs.getString("drink_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drinks;
    }

    public boolean restockFromMainBranch(String drinkName, String toBranch, int quantity) {
        Connection conn = null;
        PreparedStatement pstmtCheck = null;
        PreparedStatement pstmtUpdateFrom = null;
        PreparedStatement pstmtUpdateTo = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            int drinkId = getDrinkId(drinkName);
            int nairobiId = getBranchId("Nairobi");
            int targetId = getBranchId(toBranch);

            String checkSQL = "SELECT quantity FROM inventory WHERE branch_id = ? AND drink_id = ?";
            pstmtCheck = conn.prepareStatement(checkSQL);
            pstmtCheck.setInt(1, nairobiId);
            pstmtCheck.setInt(2, drinkId);
            ResultSet rs = pstmtCheck.executeQuery();

            if (!rs.next() || rs.getInt("quantity") < quantity) {
                conn.rollback();
                return false;
            }

            String deductSQL = "UPDATE inventory SET quantity = quantity - ? WHERE branch_id = ? AND drink_id = ?";
            pstmtUpdateFrom = conn.prepareStatement(deductSQL);
            pstmtUpdateFrom.setInt(1, quantity);
            pstmtUpdateFrom.setInt(2, nairobiId);
            pstmtUpdateFrom.setInt(3, drinkId);
            pstmtUpdateFrom.executeUpdate();

            String addSQL = """
                INSERT INTO inventory (branch_id, drink_id, quantity, min_threshold)
                VALUES (?, ?, ?, 5)
                ON DUPLICATE KEY UPDATE quantity = quantity + ?""";

            pstmtUpdateTo = conn.prepareStatement(addSQL);
            pstmtUpdateTo.setInt(1, targetId);
            pstmtUpdateTo.setInt(2, drinkId);
            pstmtUpdateTo.setInt(3, quantity);
            pstmtUpdateTo.setInt(4, quantity);
            pstmtUpdateTo.executeUpdate();

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (pstmtCheck != null) pstmtCheck.close();
                if (pstmtUpdateFrom != null) pstmtUpdateFrom.close();
                if (pstmtUpdateTo != null) pstmtUpdateTo.close();
                if (conn != null) conn.setAutoCommit(true);
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private int getDrinkId(String drinkName) throws SQLException {
        String sql = "SELECT drink_id FROM drinks WHERE drink_name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, drinkName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt("drink_id");
            }
        }
        throw new SQLException("Drink not found: " + drinkName);
    }

    public List<Inventory> getLowStock(int branchId) {
        List<Inventory> lowStockList = new ArrayList<>();
        String sql = "SELECT * FROM inventory WHERE branch_id = ? AND quantity < min_threshold";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, branchId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Inventory inventory = new Inventory();
                inventory.setInventoryId(rs.getInt("inventory_id"));
                inventory.setBranchId(rs.getInt("branch_id"));
                inventory.setDrinkId(rs.getInt("drink_id"));
                inventory.setQuantity(rs.getInt("quantity"));
                inventory.setMinThreshold(rs.getInt("min_threshold"));

                lowStockList.add(inventory);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lowStockList;
    }

}
