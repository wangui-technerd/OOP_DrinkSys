package com.drinks.demo.controller;
import  com.drinks.demo.model.Order;
import com.drinks.demo.utilities.DBHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.util.HashMap;
import java.util.List;

import com.drinks.demo.model.Order;
import com.drinks.demo.model.Branch;
import com.drinks.demo.model.Drink;



public class ReportController {

    @FXML
    private TableView<Order> customerOrdersTable;

    @FXML
    private TableView<Branch> branchSalesTable;

    @FXML
    private Label totalSalesLabel;

    @FXML
    private TableView<Drink> lowStockTable;

    @FXML private Button reportButton;
    @FXML private Label statusLabel;

    @FXML

    private void generateReport() {
        try {
            // Sample data
            List<Order> orders = DBHelper.getAllOrders();

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orders);

            JasperPrint print = JasperFillManager.fillReport(
                    getClass().getResourceAsStream("/reports/order_report.jasper"),
                    new HashMap<>(),
                    dataSource
            );

            JasperViewer.viewReport(print, false);
            statusLabel.setText("Report generated!");
        } catch (JRException e) {
            e.printStackTrace();
            statusLabel.setText("Failed to generate report.");
        }
    }



    @FXML
    private void initialize() {
        reportButton.setOnAction(e -> generateReport());

        loadReportData();
    }
    private void loadReportData() {
        customerOrdersTable.setItems(FXCollections.observableArrayList(
                DBHelper.getCustomerOrders()
        ));
        branchSalesTable.setItems(FXCollections.observableArrayList(
                DBHelper.getBranchSales()
        ));
        lowStockTable.setItems(FXCollections.observableArrayList(
                DBHelper.getLowStockItems(10)
        ));

        double total = DBHelper.getTotalSales();
        totalSalesLabel.setText(String.format("$%.2f", total));
    }
    @FXML
    private void handleRefreshReports() {
        loadReportData();
    }
}
