package com.drinks.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class OrderController {

    @FXML
    private ComboBox<String> branchComboBox;

    @FXML
    private ComboBox<String> drinkComboBox;

    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField customerContactField;

    @FXML
    private void initialize() {
        // Populate branch and drink ComboBoxes (ideally from DB)
        branchComboBox.getItems().addAll("Nairobi", "Nakuru", "Mombasa", "Kisumu");
        drinkComboBox.getItems().addAll("Cola", "Orange Juice", "Water", "Malt");
    }

    @FXML
    private void handlePlaceOrder() {
        String branch = branchComboBox.getValue();
        String drink = drinkComboBox.getValue();
        int quantity = quantitySpinner.getValue();
        String customerName = customerNameField.getText();
        String customerContact = customerContactField.getText();

        // TODO: Save order to DB and update inventory

        showAlert("Order Placed", "Your order has been placed successfully!");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
