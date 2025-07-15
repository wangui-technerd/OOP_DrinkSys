package com.drinks.demo.controller;

import com.drinks.demo.Main;
import com.drinks.demo.model.Customer;
import com.drinks.demo.service.CustomerService;
import com.drinks.demo.service.CustomerServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class viewCustomersController {

    @FXML
    private TableView<Customer> customersTable;
    @FXML
    private TableColumn<Customer, Integer> idColumn;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> contactColumn;

    private final CustomerService customerService = new CustomerServiceImpl();

    private Main mainApp;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

        ObservableList<Customer> customerData = FXCollections.observableArrayList(customerService.getAllCustomers());
        customersTable.setItems(customerData);
    }
    @FXML
    private void handleBackToAdmin() {
        // Navigate back to the Admin Dashboard
        if (mainApp != null) {
            mainApp.showAdminView();
        }
    }


    public void setMainApp(Main main) {
        this.mainApp = main;
    }
}
