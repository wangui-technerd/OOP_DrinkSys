package com.drinks.demo.controller;
import com.drinks.demo.Main;
import javafx.fxml.FXML;
public class BranchDashController {
    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleViewInventory() {
        mainApp.showInventoryView();
        System.out.println("Viewing inventory...");
    }

    @FXML
    private void handleBranchReport() {
        mainApp.showBranchReportView();
        System.out.println("Showing branch report...");
    }

    @FXML
    private void handleBackToAdmin() {
        mainApp.showAdminView();
    }
}

