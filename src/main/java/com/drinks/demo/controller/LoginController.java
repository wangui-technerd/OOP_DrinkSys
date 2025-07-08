package com.drinks.demo.controller;

import com.drinks.demo.Main;
import com.drinks.demo.utilities.DBConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    private Main mainApp;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleLogin() {
        String emailOrContact = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (emailOrContact.isEmpty() || password.isEmpty()) {
            showAlert("Validation Error", "Email/Contact and Password must not be empty.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE (email = ? OR contact = ?) AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, emailOrContact);
                stmt.setString(2, emailOrContact);
                stmt.setString(3, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String role = rs.getString("role");
                    if ("admin".equalsIgnoreCase(role)) {
                        mainApp.showAdminView();
                    } else if ("customer".equalsIgnoreCase(role)) {
                        mainApp.showCustomerView();
                    } else {
                        showAlert("Login Error", "Unknown role: " + role);
                    }
                    return;
                }
            }

            showAlert("Login Failed", "Invalid email/contact or password.");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Database Error", "Something went wrong while connecting to the database.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
