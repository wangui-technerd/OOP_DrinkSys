package com.drinks.demo.controller;

import com.drinks.demo.Main;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class CustomerDashController {
    private Main mainApp;

    @FXML
    private ComboBox<String> branchSelector;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
        branchSelector.getItems().addAll("Nairobi", "Kisumu", "Nakuru", "Mombasa");
    }

    @FXML
    private void handleMakeOrder() {
        if (branchSelector.getValue() == null) {
            return;
        }
        mainApp.showOrderView();
    }

    @FXML
    private void handlePayment() {
        System.out.println("Proceeding to payment...");
    }

    @FXML
    private void handleBackToAdmin() {
        mainApp.showCustomerView();
    }

    @FXML
    private void handleBackToWelcome() {
        mainApp.showWelcomeScreen(); // Make sure this method exists in Main.java
    }
}
