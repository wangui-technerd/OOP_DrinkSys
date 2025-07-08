package com.drinks.demo.database;

import com.drinks.demo.model.Branch;
import com.drinks.demo.utilities.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BranchDao {
    public List<Branch> getAllBranches() {
        List<Branch> branches = new ArrayList<>();
        String sql = "SELECT * FROM branches";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Branch branch = new Branch(
                        rs.getInt("branch_id"),
                        rs.getString("name"),
                        rs.getString("location")
                );
                branches.add(branch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return branches;
    }
    public List<String> getAllBranchLocations() {
        List<String> locations = new ArrayList<>();
        String sql = "SELECT location FROM branches";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                locations.add(rs.getString("location"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return locations;
    }
}