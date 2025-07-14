package com.drinks.demo.controller;
import com.drinks.demo.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
        System.out.println("Generating full report...");
    }

    @FXML
    private void handleLogout() {
        mainApp.showAdminView();
    }

    @FXML
    private void handleViewSales(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/drinks/demo/fxml/Sales.fxml"));
        Parent salesRoot = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(salesRoot));
    }

    @FXML
    private void handleViewInventory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/drinks/demo/fxml/inventory.fxml"));
        Parent inventoryRoot = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(inventoryRoot));
    }

}
