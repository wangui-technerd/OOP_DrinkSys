package com.drinks.demo.controller;
import com.drinks.demo.Main;
import com.drinks.demo.database.OrderDao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

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
private void showAlert(Alert.AlertType type, String title, String message) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
}