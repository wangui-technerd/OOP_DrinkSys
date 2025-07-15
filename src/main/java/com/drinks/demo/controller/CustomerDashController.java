package com.drinks.demo.controller;

import com.drinks.demo.Main;
import javafx.fxml.FXML;

public class CustomerDashController {

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleMakeOrder() {
        // Since we removed branch selection, proceed directly
        mainApp.showOrderView();
    }

    @FXML
    private void handleBackToWelcome() {
        mainApp.showWelcomeScreen(); // Ensure this method exists in Main.java
    }
}
