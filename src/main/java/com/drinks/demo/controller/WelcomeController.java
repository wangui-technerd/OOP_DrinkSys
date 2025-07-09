package com.drinks.demo.controller;

import com.drinks.demo.Main;
import javafx.fxml.FXML;

public class WelcomeController {

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void goToLogin() {
        if (mainApp != null) {
            mainApp.showLoginView();
        }
    }

    @FXML
    public void goToRegister() {
        if (mainApp != null) {
            mainApp.showRegisterView();
        }
    }

    @FXML
    public void goToOrder() {
        if (mainApp != null) {
            System.out.println("Navigating to Order page...");
            mainApp.showOrderView();
        }
    }
}
