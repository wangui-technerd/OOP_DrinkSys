package com.drinks.demo.controller;

import com.drinks.demo.database.BranchDao;
import com.drinks.demo.database.DrinkDao;
import com.drinks.demo.database.OrderDao;
import com.drinks.demo.database.UsersDAO;
import com.drinks.demo.model.Branch;
import com.drinks.demo.model.Drink;
import com.drinks.demo.model.Order;
import com.drinks.demo.model.OrderDetail;
import com.drinks.demo.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderController {

    @FXML private ComboBox<Branch> branchComboBox;
    @FXML private ComboBox<Drink> drinkComboBox;
    @FXML private Spinner<Integer> quantitySpinner;
    @FXML private TextField customerNameField;
    @FXML private TextField customerContactField;
    @FXML private ListView<String> orderSummaryList;

    private final BranchDao branchDao = new BranchDao();
    private final DrinkDao drinkDao = new DrinkDao();
    private final OrderDao orderDao = new OrderDao();
    private final UsersDAO userDao = new UsersDAO();

    private final ObservableList<Branch> branches = FXCollections.observableArrayList();
    private final ObservableList<Drink> drinks = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Spinner for quantity
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        quantitySpinner.setValueFactory(valueFactory);

        loadBranches();
        loadDrinks();

        branchComboBox.setItems(branches);
        drinkComboBox.setItems(drinks);
    }

    private void loadBranches() {
        branches.clear();
        branches.addAll(branchDao.getAllBranches());
    }

    private void loadDrinks() {
        drinks.clear();
        drinks.addAll(drinkDao.getAllDrinks());
    }

    @FXML
    private void handlePlaceOrder() {
        String name = customerNameField.getText();
        String contact = customerContactField.getText();
        Branch branch = branchComboBox.getValue();
        Drink drink = drinkComboBox.getValue();
        int quantity = quantitySpinner.getValue();

        if (name.isEmpty() || contact.isEmpty() || branch == null || drink == null) {
            showAlert("Missing Info", "Please fill all fields and select options.");
            return;
        }

        // Create or retrieve user
        User user = userDao.getUserByEmail(contact + "@autogen.com"); // Fake email for lookup
        if (user == null) {
            user = new User(name, contact, contact + "@autogen.com", "1234", "customer");
            userDao.registerUser(user);
            user = userDao.getUserByEmail(contact + "@autogen.com");
        }

        double total = drink.getPrice() * quantity;
        Timestamp now = Timestamp.from(Instant.now());

        Order order = new Order();
        order.setCustomerId(user.getUserId());
        order.setCustomerName(user.getName());
        order.setBranchId(branch.getBranchId());
        order.setBranchName(branch.getLocation());
        order.setDrinkName(drink.getName());
        order.setQuantity(quantity);
        order.setPrice(drink.getPrice());
        order.setOrderDate(now);
        order.setTotalAmount(total);

        int orderId = orderDao.addOrder(order);

        if (orderId > 0) {
            OrderDetail detail = new OrderDetail(0, drink.getName(), orderId, drink.getDrinkId(), quantity, drink.getPrice());

            orderDao.addOrderDetail(detail);

            orderSummaryList.getItems().clear();
            orderSummaryList.getItems().add("Order ID: " + orderId);
            orderSummaryList.getItems().add("Customer: " + name);
            orderSummaryList.getItems().add("Drink: " + drink.getName());
            orderSummaryList.getItems().add("Quantity: " + quantity);
            orderSummaryList.getItems().add("Price per Unit: " + drink.getPrice());
            orderSummaryList.getItems().add("Branch: " + branch.getLocation());
            orderSummaryList.getItems().add("Total: " + total);
        } else {
            showAlert("Error", "Order failed to save.");
        }
    }

    private void showAlert(String title, String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setContentText(message);
        a.showAndWait();
    }
}
