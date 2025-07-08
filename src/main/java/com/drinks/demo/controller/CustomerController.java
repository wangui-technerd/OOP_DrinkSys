package com.drinks.demo.controller;

import com.drinks.demo.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class CustomerController {
    @FXML private AnchorPane rootPane;
    @FXML private TextField customerNameField;
    @FXML private TextField contactField;
    @FXML private Button addButton;
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> idColumn;
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Customer, String> contactColumn;

    private final ObservableList<Customer> customerData = FXCollections.observableArrayList();

    private com.drinks.demo.Main mainApp;

    public void setMainApp(com.drinks.demo.Main mainApp) {
        this.mainApp = mainApp;
    }




    @FXML
    private void initialize() {
        // Configure table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

        // Load sample data
        loadSampleCustomers();

        // Set up add button
        addButton.setOnAction(event -> addCustomer());
    }

    private void loadSampleCustomers() {
        customerData.add(new Customer(1, "John Doe", "john@example.com"));
        customerData.add(new Customer(2, "Jane Smith", "jane@example.com"));
        customerTable.setItems(customerData);
    }

    private void addCustomer() {
        String name = customerNameField.getText();
        String contact = contactField.getText();

        if (!name.isEmpty() && !contact.isEmpty()) {
            int newId = customerData.isEmpty() ? 1 : customerData.getLast().getCustomerId() + 1;
            customerData.add(new Customer(newId, name, contact));

            // Clear fields
            customerNameField.clear();
            contactField.clear();
        }
    }
}