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
        branchSelector.getItems().addAll("Nairobi", "Kisumu", "Nakuru","Mombasa");
    }

    @FXML
    private void handleMakeOrder() {
        System.out.println("Order started for " + branchSelector.getValue());
    }

    @FXML
    private void handlePayment() {
        System.out.println("Proceeding to payment...");
    }

    @FXML
    private void handleBackToAdmin() {
        mainApp.showAdminView();
    }
}

