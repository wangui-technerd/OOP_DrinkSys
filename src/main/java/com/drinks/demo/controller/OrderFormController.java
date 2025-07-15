package com.drinks.demo.controller;

import com.drinks.demo.Main;
import com.drinks.demo.model.Branch;
import com.drinks.demo.model.Drink;
import com.drinks.demo.model.Order;
import com.drinks.demo.model.OrderDetail;
import com.drinks.demo.service.OrderService;
import com.drinks.demo.service.OrderServiceImpl;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.util.*;

public class OrderFormController {

    private final OrderService orderService = new OrderServiceImpl();

    @FXML private ComboBox<String> branchSelector;
    @FXML private Label branchNotice;
    @FXML private VBox orderSection;

    @FXML private ComboBox<String> drinkTypeCombo;
    @FXML private ComboBox<String> drinkBrandCombo;
    @FXML private TextField qtyField;

    @FXML private ListView<String> orderList;
    @FXML private Label totalCost;

    private final List<OrderDetail> orderDetails = new ArrayList<>();
    private final Map<String, Integer> branchNameToId = new HashMap<>();
    private final Map<String, List<Drink>> typeToBrands = new HashMap<>();
    private double total = 0;
    private Main mainApp;

    @FXML
    public void initialize() {
        // Load branches
        List<Branch> branches = orderService.getAllBranches();
        for (Branch b : branches) {
            branchSelector.getItems().add(b.getLocation());
            branchNameToId.put(b.getLocation(), b.getBranchId());
        }

        // Load drink types and brands
        List<Drink> drinks = orderService.getAllDrinks();
        Set<String> types = new HashSet<>();
        for (Drink d : drinks) {
            types.add(d.getName());
            typeToBrands.computeIfAbsent(d.getName(), k -> new ArrayList<>()).add(d);
        }
        drinkTypeCombo.getItems().addAll(types);

        // When type is selected, populate brands
        drinkTypeCombo.setOnAction(e -> {
            String selectedType = drinkTypeCombo.getValue();
            drinkBrandCombo.getItems().clear();
            if (selectedType != null) {
                for (Drink d : typeToBrands.get(selectedType)) {
                    drinkBrandCombo.getItems().add(d.getBrand());
                }
            }
        });

        branchNotice.setVisible(true);
        orderSection.setVisible(false);
    }

    @FXML
    public void onBranchSelected() {
        if (branchSelector.getValue() != null) {
            branchNotice.setVisible(false);
            orderSection.setVisible(true);
        }
    }

    @FXML
    public void addDrink() {
        String type = drinkTypeCombo.getValue();
        String brand = drinkBrandCombo.getValue();
        String qtyStr = qtyField.getText();

        if (type == null || brand == null || qtyStr == null || qtyStr.isEmpty()) {
            showAlert("Please select type, brand, and enter quantity.");
            return;
        }

        try {
            int qty = Integer.parseInt(qtyStr);
            if (qty <= 0) {
                showAlert("Quantity must be greater than 0.");
                return;
            }

            // Find the drink
            Drink selectedDrink = null;
            for (Drink d : typeToBrands.get(type)) {
                if (d.getBrand().equals(brand)) {
                    selectedDrink = d;
                    break;
                }
            }
            if (selectedDrink == null) {
                showAlert("Drink not found.");
                return;
            }

            double subtotal = qty * selectedDrink.getPrice();
            orderList.getItems().add(type + " - " + brand + " x " + qty + " = " + subtotal + " KES");
            total += subtotal;
            totalCost.setText(String.valueOf(total));

            OrderDetail detail = new OrderDetail();
            detail.setDrinkId(selectedDrink.getDrinkId());
            detail.setDrinkName(type);
            detail.setBrand(brand);
            detail.setQuantity(qty);
            detail.setPrice(selectedDrink.getPrice());
            orderDetails.add(detail);

            qtyField.clear();

        } catch (NumberFormatException e) {
            showAlert("Enter a valid quantity.");
        }
    }

    @FXML
    public void placeOrder() {
        if (orderDetails.isEmpty()) {
            showAlert("Please add items to the order.");
            return;
        }

        String selectedBranch = branchSelector.getValue();
        if (!branchNameToId.containsKey(selectedBranch)) {
            showAlert("Invalid branch selected.");
            return;
        }

        Order order = new Order();
        order.setCustomerId(1); // Static for demo
        order.setBranchId(branchNameToId.get(selectedBranch));
        order.setTotalAmount(total);

        int orderId = orderService.placeOrder(order, orderDetails);

        if (orderId > 0) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/drinks/demo/fxml/payment.fxml"));
                Parent root = loader.load();

                // Pass data to PaymentController
                PaymentController paymentController = loader.getController();
                paymentController.setBranch(selectedBranch);
                paymentController.setOrderData(orderId);

                Stage stage = (Stage) branchSelector.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error loading payment page.");
            }

            orderDetails.clear();
            orderList.getItems().clear();
            total = 0;
            totalCost.setText("0");

        } else {
            showAlert("Failed to place order.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void setMainApp(Main main) {
        this.mainApp = main;
    }

    @FXML
    private void goBack() {
        if (mainApp != null) {
            mainApp.showCustomerView(); // Or showAdminView() depending on user type
        }
    }
}
