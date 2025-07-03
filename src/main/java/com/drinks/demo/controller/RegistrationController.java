package com.drinks.demo.controller;

import com.drinks.demo.model.User;
import com.drinks.demo.service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegistrationController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private ChoiceBox<String> roleChoiceBox;

    private final AuthService authService = new AuthService();

    private com.drinks.demo.Main mainApp;

    public void setMainApp(com.drinks.demo.Main mainApp) {
        this.mainApp = mainApp;
    }


    @FXML
    public void initialize() {
        // Populate roles
        roleChoiceBox.getItems().addAll("customer", "admin");
    }

    @FXML
    public void handleRegister() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String role = roleChoiceBox.getValue();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || role == null) {
            showAlert(Alert.AlertType.ERROR, "All fields are required.");
            return;
        }

        User user = new User(name, email, password, role);
        boolean success = authService.registerUser(user);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Registration successful! Please log in.");
            clearFields();

            // ✅ Switch to login screen
            if (mainApp != null) {
                mainApp.showLoginView();
            }
        }

    }

    private void clearFields() {
        nameField.clear();
        emailField.clear();
        passwordField.clear();
        roleChoiceBox.setValue(null);
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
