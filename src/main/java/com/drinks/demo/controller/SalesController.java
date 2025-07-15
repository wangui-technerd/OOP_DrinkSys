package com.drinks.demo.controller;

import com.drinks.demo.database.InventoryDAO;
import com.drinks.demo.database.OrderDao;
import com.drinks.demo.model.Inventory;
import com.drinks.demo.model.Order;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class SalesController {

    @FXML private Label lowStockNotification;

    @FXML private ComboBox<String> branchFilter;
    @FXML private ComboBox<String> drinkTypeFilter;
    @FXML private ComboBox<String> restockBranch;
    @FXML private ComboBox<String> restockDrink;
    @FXML private TextField restockAmount;

    @FXML private TableView<Order> salesTable;
    @FXML private TableColumn<Order, String> dateCol, branchCol, drinkCol;
    @FXML private TableColumn<Order, Integer> qtyCol;
    @FXML private TableColumn<Order, Double> amountCol;

    @FXML private TableView<Inventory> inventoryTable;
    @FXML private TableColumn<Inventory, String> invDrinkCol;
    @FXML private TableColumn<Inventory, Integer> invQtyCol;

    private final OrderDao orderDAO = new OrderDao();
    private final InventoryDAO inventoryDAO = new InventoryDAO();

    @FXML
    public void initialize() {
        // Setup branch list
        ObservableList<String> branches = FXCollections.observableArrayList("Nairobi", "Nakuru", "Kisumu", "Mombasa");
        branchFilter.setItems(branches);
        restockBranch.setItems(FXCollections.observableArrayList("Nakuru", "Kisumu", "Mombasa")); // Nairobi excluded

        // Populate drink filter and drink restock combo box from inventory
        List<String> allDrinks = inventoryDAO.getAllDrinkNames();
        drinkTypeFilter.setItems(FXCollections.observableArrayList(allDrinks));
        restockDrink.setItems(FXCollections.observableArrayList(allDrinks));

        // Setup table columns
        setupSalesTable();
        setupInventoryTable();

        // Load all sales and Nairobi inventory initially
        loadSales(null, null);
        loadInventory("Nairobi");
        showLowStock("Nairobi");
    }

    private void setupSalesTable() {
        dateCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getOrderDate().toLocalDateTime().toLocalDate().toString()));
        branchCol.setCellValueFactory(data -> data.getValue().branchProperty());
        drinkCol.setCellValueFactory(data -> data.getValue().drinkProperty());
        qtyCol.setCellValueFactory(data -> data.getValue().quantityProperty().asObject());
        amountCol.setCellValueFactory(data -> data.getValue().amountProperty().asObject());
    }

    private void setupInventoryTable() {
        invDrinkCol.setCellValueFactory(data -> data.getValue().drinkNameProperty());
        invQtyCol.setCellValueFactory(data -> data.getValue().quantityProperty().asObject());
    }

    @FXML
    public void showAllSales() {
        String branch = branchFilter.getValue();
        String drink = drinkTypeFilter.getValue();
        loadSales(branch, drink);
        if (branch != null) {
            loadInventory(branch);
            showLowStock(branch);
        }
    }

    private void loadSales(String branch, String drinkType) {
        List<Order> sales = orderDAO.getOrdersFiltered(branch, drinkType);
        salesTable.setItems(FXCollections.observableArrayList(sales));
    }

    private void loadInventory(String branch) {
        List<Inventory> stock = inventoryDAO.getInventoryByBranch(branch);
        inventoryTable.setItems(FXCollections.observableArrayList(stock));
    }

    private void showLowStock(String branch) {
        List<Inventory> low = inventoryDAO.getLowStockItems(branch);
        if (!low.isEmpty()) {
            StringBuilder msg = new StringBuilder("Low stock in " + branch + ": ");
            for (Inventory item : low) {
                msg.append(item.getDrinkName()).append(" (").append(item.getQuantity()).append("), ");
            }
            lowStockNotification.setText(msg.toString().replaceAll(", $", ""));
        } else {
            lowStockNotification.setText("");
        }
    }

    @FXML
    public void restockDrinkToBranch() {
        String targetBranch = restockBranch.getValue();
        String drink = restockDrink.getValue();
        int quantity;

        try {
            quantity = Integer.parseInt(restockAmount.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid quantity entered.");
            return;
        }

        if (inventoryDAO.restockFromMainBranch(drink, targetBranch, quantity)) {
            showAlert("Restock successful!");
            loadInventory(targetBranch);
        } else {
            showAlert("Insufficient stock in Nairobi or error occurred.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sales Management");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
