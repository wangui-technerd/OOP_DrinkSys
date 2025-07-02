package com.drinks.demo.controller;

import com.drinks.demo.Main;
import javafx.fxml.FXML;

public class WelcomeController {
    private Main mainApp;

    public void setMainApp(Main mainApp) {
        System.out.println("Main App injected into controller.");
        this.mainApp = mainApp;
    }

//    @FXML
//    private void goToLogin() {
//        mainApp.showLoginView();
//    }

//    @FXML
//    private void goToRegister() {
//        mainApp.showRegisterView();
//    }

}
