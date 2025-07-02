package com.drinks.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class ReportController {

    @FXML
    private TableView<?> customerOrdersTable;

    @FXML
    private TableView<?> branchSalesTable;

    @FXML
    private Label totalSalesLabel;

    @FXML
    private TableView<?> lowStockTable;

    @FXML
    private void initialize() {
        // TODO: Load data from DB and populate tables/labels
    }

    @FXML
    private void handleRefreshReports() {
        // TODO: Reload data from DB
    }
}
