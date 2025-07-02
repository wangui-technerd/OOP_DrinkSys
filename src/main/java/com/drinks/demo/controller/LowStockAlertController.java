package com.drinks.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class LowStockAlertController {

    @FXML
    private TableView<?> lowStockTable;

    @FXML
    private void initialize() {
        // TODO: Populate lowStockTable with drinks below threshold from DB
    }
}
