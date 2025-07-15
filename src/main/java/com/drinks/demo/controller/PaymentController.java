package com.drinks.demo.controller;

import com.drinks.demo.model.Order;
import com.drinks.demo.service.OrderService;
import com.drinks.demo.service.OrderServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class PaymentController {
    @FXML private Label customerName;
    @FXML private Label orderId;
    @FXML private Label totalAmount;
    @FXML private ComboBox<String> paymentMethod;

    private final OrderService orderService = new OrderServiceImpl();
    private Order currentOrder;

    public void setOrderData(int orderIdFromPreviousPage) {
        currentOrder = orderService.getOrderById(orderIdFromPreviousPage);

        if (currentOrder != null) {
            orderId.setText("ORD-" + currentOrder.getOrderId());
            totalAmount.setText(String.format("%.2f", currentOrder.getTotalAmount()) + " KES");
            customerName.setText("Customer #" + currentOrder.getCustomerId());
        } else {
            showAlert("Order not found for ID: " + orderIdFromPreviousPage);
        }
    }

    @FXML
    public void initialize() {
        paymentMethod.getItems().add("Mpesa");
        paymentMethod.setValue("Mpesa");
    }

    @FXML
    private void submitPayment() {
        if (currentOrder == null) {
            showAlert("Order data not loaded.");
            return;
        }

        String method = paymentMethod.getValue();
        boolean saved = orderService.savePayment(currentOrder.getOrderId(), method, "N/A");

        if (saved) {
            showAlert("✅ Payment recorded successfully!");
            System.out.println("✅ Payment saved for Order ID: " + currentOrder.getOrderId());
        } else {
            showAlert("❌ Failed to save payment.");
            System.err.println("❌ Payment failed for Order ID: " + currentOrder.getOrderId());
        }
    }

    @FXML
    private void goBackToOrder(ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/com/drinks/demo/fxml/order_form.fxml"));
            javafx.scene.Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading order form.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText(msg);
        alert.showAndWait();
    }

}