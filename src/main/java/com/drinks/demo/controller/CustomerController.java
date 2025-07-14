package com.drinks.demo.controller;

import com.drinks.demo.database.BranchDao;
import com.drinks.demo.database.OrderDao;
import com.drinks.demo.model.Branch;
import com.drinks.demo.model.Customer;
import com.drinks.demo.model.Order;
import javafx.beans.property.SimpleStringProperty;
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
    @FXML private ComboBox<Branch> branchComboBox;
    @FXML private TableView<Order> orderTable;
    @FXML private TableColumn<Order, Integer> orderIdColumn;
    @FXML private TableColumn<Order, String> customerNameColumn;
    @FXML private TableColumn<Order, String> productColumn;
    @FXML private TableColumn<Order, Integer> quantityColumn;
    @FXML private TableColumn<Order, Double> priceColumn;
    @FXML private TableColumn<Order, String> branchNameColumn;


    private final ObservableList<Branch> branches = FXCollections.observableArrayList();
    private final ObservableList<Order> orders = FXCollections.observableArrayList();
    private final ObservableList<Customer> customerData = FXCollections.observableArrayList();

    private final OrderDao orderDao = new OrderDao(); // assumes constructor is parameterless
    private final BranchDao branchDao = new BranchDao();

    @FXML
    private void initialize() {
        // Setup customer table
        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));


        customerTable.setItems(customerData);
        loadSampleCustomers();

        // Setup branch ComboBox
        branchComboBox.setItems(branches);
        loadBranches(); // from database
        branchComboBox.setOnAction(e -> loadOrdersForBranch(branchComboBox.getValue()));

        // Setup order table
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        productColumn.setCellValueFactory(new PropertyValueFactory<>("drinkName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        branchNameColumn.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        orderTable.setItems(orders);

        // Add customer button
        addButton.setOnAction(event -> addCustomer());
    }

    private void loadBranches() {
        branches.clear();
        branches.addAll(branchDao.getAllBranches()); // Fetch from DB
    }

    private void loadOrdersForBranch(Branch selectedBranch) {
        orders.clear();
        if (selectedBranch != null) {
            var loadedOrders = orderDao.getOrdersByBranchId(selectedBranch.getBranchId());
            for (Order o : loadedOrders) {
                System.out.println("Branch from Order Object: " + o.getBranchName());
            }
            orders.addAll(loadedOrders);
            orderTable.refresh();
            orderTable.getItems().clear();
            orderTable.getItems().addAll(loadedOrders);

        }
    }

    private void loadSampleCustomers() {
        customerData.add(new Customer(1, "John Doe", "john@example.com"));
        customerData.add(new Customer(2, "Jane Smith", "jane@example.com"));
    }

    private void addCustomer() {
        String name = customerNameField.getText();
        String contact = contactField.getText();

        if (!name.isEmpty() && !contact.isEmpty()) {
            int newId = customerData.isEmpty() ? 1 : customerData.get(customerData.size() - 1).getCustomerId() + 1;
            customerData.add(new Customer(newId, name, contact));
            customerNameField.clear();
            contactField.clear();
        }
    }
}
