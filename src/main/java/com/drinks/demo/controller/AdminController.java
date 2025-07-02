package com.drinks.demo.controller;

import com.drinks.demo.model.Admin;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class AdminController {
    @FXML private AnchorPane rootPane;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label statusLabel;

    @FXML
    private void initialize() {
        // Set up login button action
        loginButton.setOnAction(event -> handleLogin());

        // Optional: Add keyboard listener for Enter key
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Username and password are required!");
            return;
        }

        // In a real application, you would validate against a database
        Admin admin = new Admin(1, username, password);

        if (authenticateAdmin(admin)) {
            statusLabel.setText("Login successful!");
            // TODO: Load admin dashboard
        } else {
            statusLabel.setText("Invalid credentials!");
        }
    }

    private boolean authenticateAdmin(Admin admin) {
        // Replace with actual authentication logic
        return admin.getUsername().equals("admin") &&
                admin.getPassword().equals("admin123");
    }
}