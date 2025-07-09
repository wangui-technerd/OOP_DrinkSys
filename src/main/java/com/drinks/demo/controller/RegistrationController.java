package com.drinks.demo.controller;

import com.drinks.demo.Main;
import com.drinks.demo.utilities.DBConnection;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegistrationController {

    private Main mainApp;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ChoiceBox<String> roleChoiceBox;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
        roleChoiceBox.getItems().addAll("Customer", "Admin");
        roleChoiceBox.setValue("Customer");
    }

    @FXML
    private void handleRegister() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String role = roleChoiceBox.getValue().toLowerCase();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || role == null) {
            showAlert("Validation Error", "Please fill in all fields.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {


            String sql = "INSERT INTO users (name, email, password,role) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, role);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                showAlert("Success", role.substring(0, 1).toUpperCase() + role.substring(1) + " registered successfully!");
                mainApp.showLoginView();
            } else {
                showAlert("Error", "Failed to register user.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Database Error", "Error:" + e.getMessage());
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
