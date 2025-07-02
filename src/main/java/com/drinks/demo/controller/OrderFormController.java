package com.drinks.demo.controller;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

public class OrderFormController {

    private final OrderService orderService = new OrderServiceImpl();

    @FXML private ComboBox<String> branchSelector;
    @FXML private Label branchNotice;
    @FXML private VBox orderSection;

    @FXML private ComboBox<String> coffeeType;
    @FXML private ComboBox<String> energyType;
    @FXML private ComboBox<String> juiceType;
    @FXML private ComboBox<String> sodaType;

    @FXML private TextField qtyCoffee;
    @FXML private TextField qtyEnergy;
    @FXML private TextField qtyJuice;
    @FXML private TextField qtySoda;

    @FXML private ListView<String> orderList;
    @FXML private Text totalCost;

    private final List<OrderDetail> orderDetails = new ArrayList<>();
    private final Map<String, Integer> drinkNameToId = new HashMap<>();
    private final Map<String, Integer> branchNameToId = new HashMap<>();
    private int total = 0;

    @FXML
    public void initialize() {
        // Load branches
        List<Branch> branches = orderService.getAllBranches();
        for (Branch b : branches) {
            branchSelector.getItems().add(b.getLocation());
            branchNameToId.put(b.getLocation(), b.getBranchId());
        }

        // Load drinks
        List<Drink> drinks = orderService.getAllDrinks();
        for (Drink d : drinks) {
            drinkNameToId.put(d.getName(), d.getDrinkId());
        }

        // Populate UI drink types
        coffeeType.getItems().addAll(orderService.getDrinkTypesByCategory("Coffee"));
        energyType.getItems().addAll(orderService.getDrinkTypesByCategory("Energy"));
        juiceType.getItems().addAll(orderService.getDrinkTypesByCategory("Juice"));
        sodaType.getItems().addAll(orderService.getDrinkTypesByCategory("Soda"));

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

    public void addCoffee() {
        addItem(coffeeType.getValue(), qtyCoffee, 1200);
    }

    public void addEnergy() {
        addItem(energyType.getValue(), qtyEnergy, 1800);
    }

    public void addJuice() {
        addItem(juiceType.getValue(), qtyJuice, 900);
    }

    public void addSoda() {
        addItem(sodaType.getValue(), qtySoda, 1100);
    }

    private void addItem(String drinkName, TextField qtyField, int pricePerUnit) {
        if (drinkName == null || drinkName.isEmpty()) {
            showAlert("Please select a drink type.");
            return;
        }

        try {
            int qty = Integer.parseInt(qtyField.getText());
            if (qty <= 0) {
                showAlert("Quantity must be greater than 0.");
                return;
            }

            int subtotal = qty * pricePerUnit;
            orderList.getItems().add(drinkName + " x " + qty + " = " + subtotal + " KES");
            total += subtotal;
            totalCost.setText(String.valueOf(total));

            OrderDetail detail = new OrderDetail();
            detail.setDrinkName(drinkName); // optional, just for local display
            detail.setQuantity(qty);
            detail.setPrice(pricePerUnit);
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

        // Map drink names to drink IDs
        for (OrderDetail detail : orderDetails) {
            Integer drinkId = drinkNameToId.get(detail.getDrinkName());
            if (drinkId == null) {
                showAlert("Unknown drink: " + detail.getDrinkName());
                return;
            }
            detail.setDrinkId(drinkId);
        }

        Order order = new Order();
        order.setCustomerId(1); // Static customer for demo
        order.setBranchId(branchNameToId.get(selectedBranch));
        order.setTotalAmount(total); // assuming it's double

        int orderId = orderService.placeOrder(order, orderDetails);

        if (orderId > 0) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Payment.fxml")); // ✅ Update path if needed
                Parent root = loader.load();

                com.drinks.demo.controller.PaymentController controller = loader.getController();
                controller.setOrderData(orderId);  // ✅ Send orderId to payment screen

                Stage stage = new Stage();
                stage.setTitle("Payment");
                stage.setScene(new Scene(root));
                stage.show();

                // Optional: close order window
                ((Stage) orderList.getScene().getWindow()).close();

            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error loading payment screen.");
            }
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
}
