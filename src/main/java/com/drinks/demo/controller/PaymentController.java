package com.drinks.demo.controller;

import com.drinks.demo.model.Order;
import com.drinks.demo.service.OrderService;
import com.drinks.demo.service.OrderServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PaymentController {

    @FXML private Label customerName;
    @FXML private Label orderId;
    @FXML private Label totalAmount;
    @FXML private ComboBox<String> paymentMethod;
    @FXML private TextField transactionCode;

    private final OrderService orderService = new OrderServiceImpl();
    private Order currentOrder;
    private String branch;

    public void setOrderData(int orderIdFromPreviousPage) {
        currentOrder = orderService.getOrderById(orderIdFromPreviousPage);

        if (currentOrder != null) {
            this.orderId.setText("ORD-" + currentOrder.getOrderId());
            this.totalAmount.setText(String.format("%.2f", currentOrder.getTotalAmount()) + " KES");
        } else {
            System.out.println("Order not found for ID: " + orderIdFromPreviousPage);
        }
    }

    public void setBranch(String branch) {
        this.branch = branch;
        System.out.println("Received branch: " + branch);
    }

    @FXML
    public void initialize() {
        paymentMethod.getItems().addAll("Mpesa", "Cash", "Card");
    }

    @FXML
    private void submitPayment() {
        String method = paymentMethod.getValue();
        String code = transactionCode.getText();

        if (method == null || method.isEmpty()) {
            showAlert("Please select a payment method.");
            return;
        }

        boolean saved = orderService.savePayment(currentOrder.getOrderId(), method, code);

        if (saved) {
            showAlert("Payment recorded successfully!");
        } else {
            showAlert("Failed to save payment.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
