package com.drinks.demo.database;

import com.drinks.demo.model.Drink;
import com.drinks.demo.utilities.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DrinkDao {
    public List<Drink> getAllDrinks() {
        List<Drink> drinks = new ArrayList<>();
        String sql = "SELECT * FROM drinks";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Drink drink = new Drink(
                        rs.getInt("drink_id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getDouble("price")
                );
                drinks.add(drink);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drinks;
    }
}
