package com.drinks.demo.controller;
import com.drinks.demo.Main;
import javafx.event.ActionEvent;
import com.drinks.demo.database.OrderDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.Alert;

public class AdminDashController {
    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleViewCustomers() {
        mainApp.showViewCustomers();
    }

    @FXML
    private void handleViewBranches() {
        mainApp.showBranchView();
    }

    @FXML
    private void handleGenerateReports() {
       try{
        System.out.println("Generating full report...");
        OrderDao reportDao = new OrderDao();
        String reportPath = reportDao.generateFullReport();

        showAlert(Alert.AlertType.INFORMATION, "Report Generated",
                "Report successfully created at: " + reportPath);
    } catch (Exception e) {
        showAlert(Alert.AlertType.ERROR, "Report Generation Failed",
                "Error: " + e.getMessage());
        e.printStackTrace();
    }
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

private void showAlert(Alert.AlertType type, String title, String message) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
}