package com.drinks.demo.controller;

import com.drinks.demo.database.InventoryDAO;
import com.drinks.demo.model.Inventory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Arrays;
import java.util.List;

public class InventoryController {

    @FXML private ComboBox<String> branchComboBox;
    @FXML private TableView<Inventory> inventoryTable;
    @FXML private TableColumn<Inventory, Integer> drinkIdCol;
    @FXML private TableColumn<Inventory, Integer> quantityCol;
    @FXML private TableColumn<Inventory, Integer> minThresholdCol;
    @FXML private TableColumn<Inventory, String> drinkNameCol;
    @FXML private TableColumn<Inventory, String> branchNameCol;
    @FXML private Label lowStockLabel;

    private final InventoryDAO inventoryDAO = new InventoryDAO();

    @FXML
    public void initialize() {
        // Set up column mappings using wrappers
        drinkIdCol.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getDrinkId()));
        quantityCol.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getQuantity()));
        minThresholdCol.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getMinThreshold()));

        // Optional: show drink name and branch name
        if (drinkNameCol != null)
            drinkNameCol.setCellValueFactory(cellData ->
                    new ReadOnlyObjectWrapper<>(cellData.getValue().getDrinkName()));

        if (branchNameCol != null)
            branchNameCol.setCellValueFactory(cellData ->
                    new ReadOnlyObjectWrapper<>(cellData.getValue().getBranchName()));

        // Load branches
        List<String> branches = Arrays.asList("Nairobi", "Nakuru", "Mombasa", "Kisumu");
        branchComboBox.setItems(FXCollections.observableArrayList(branches));
    }

    @FXML
    private void handleLoadInventory() {
        String selectedBranch = branchComboBox.getValue();
        if (selectedBranch == null) {
            showAlert("Please select a branch first.");
            return;
        }

        List<Inventory> inventoryList = inventoryDAO.getInventoryByBranch(selectedBranch);
        ObservableList<Inventory> inventoryData = FXCollections.observableArrayList(inventoryList);
        inventoryTable.setItems(inventoryData);

        // Handle low stock warning
        List<Inventory> lowStockItems = inventoryDAO.getLowStockItems(selectedBranch);
        if (!lowStockItems.isEmpty()) {
            lowStockLabel.setText("Low stock items found in " + selectedBranch);
        } else {
            lowStockLabel.setText("Inventory is sufficient.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
