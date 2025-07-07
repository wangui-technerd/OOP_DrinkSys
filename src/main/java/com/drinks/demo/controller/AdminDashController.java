package com.drinks.demo.controller;
import com.drinks.demo.Main;
import javafx.fxml.FXML;

public class AdminDashController {
    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleViewCustomers() {
        mainApp.showCustomerView();
    }

    @FXML
    private void handleViewBranches() {
        mainApp.showBranchView();
    }

    @FXML
    private void handleGenerateReports() {
        // TODO: Add full report generation logic
        System.out.println("Generating full report...");
    }

    @FXML
    private void handleLogout() {
        mainApp.showAdminView();
    }
}
