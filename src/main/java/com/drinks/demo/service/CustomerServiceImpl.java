package com.drinks.demo.service;

import com.drinks.demo.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private static final String URL = "jdbc:mysql://localhost:3306/educrack_db";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // replace with your actual password

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        String sql = "SELECT id, full_name, email FROM users WHERE role = 'CUSTOMER'";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("full_name");
                String contact = rs.getString("email"); // or phone if you have that
                customers.add(new Customer(id, name, contact));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }
}
