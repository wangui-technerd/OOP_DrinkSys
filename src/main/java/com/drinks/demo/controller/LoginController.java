package com.drinks.demo.controller;

import com.drinks.demo.model.User;
import com.drinks.demo.service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    private final AuthService authService = new AuthService();
    private com.drinks.demo.Main mainApp;

    public void setMainApp(com.drinks.demo.Main mainApp) {
        this.mainApp = mainApp;
    }


    @FXML
    public void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Both fields are required.");
            return;
        }

        User user = authService.login(email, password);
        if (user != null) {
            showAlert(Alert.AlertType.INFORMATION, "Welcome, " + user.getName());

            // Load dashboard depending on role
            String fxml = user.getRole().equalsIgnoreCase("admin") ?
                    "/com/drinks/demo/views/AdminDashboard.fxml" :
                    "/com/drinks/demo/views/CustomerDashboard.fxml";

            try {
                Parent dashboard = FXMLLoader.load(getClass().getResource(fxml));
                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(new Scene(dashboard, 600, 400));
                stage.setTitle(user.getRole() + " Dashboard");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Failed to load dashboard: " + e.getMessage());
                e.printStackTrace();
            }

        } else {
            showAlert(Alert.AlertType.ERROR, "Invalid email or password.");
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
