package com.drinks.demo.controller;

import com.drinks.demo.model.Order;
import com.drinks.demo.model.Customer; // assuming you have one
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

    public void setOrderData(int orderIdFromPreviousPage) {
        // Fetch order from DB
        currentOrder = orderService.getOrderById(orderIdFromPreviousPage);

        if (currentOrder != null) {
            this.orderId.setText("ORD-" + currentOrder.getOrderId());
            this.totalAmount.setText(String.format("%.2f", currentOrder.getTotalAmount()) + " KES");

            // fetch customer name from DB
            String name = orderService.getCustomerNameById(currentOrder.getCustomerId());
            this.customerName.setText(name);
        }
    }

    @FXML
    public void submitPayment() {
        String method = paymentMethod.getValue();
        String code = transactionCode.getText();

        if (method == null || method.isEmpty()) {
            showAlert("Please select a payment method.");
            return;
        }

        // Save payment to DB (example placeholder)
        boolean success = orderService.savePayment(currentOrder.getOrderId(), method, code);

        if (success) {
            showAlert("Payment successful for " + currentOrder.getOrderId());
        } else {
            showAlert("Payment failed.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
