package com.drinks.demo.service;

import com.drinks.demo.model.Order;
import com.drinks.demo.model.OrderDetail;
import com.drinks.demo.model.Drink;
import com.drinks.demo.model.Branch;

import java.util.List;

public interface OrderService {
    int placeOrder(Order order, List<OrderDetail> orderDetails);

    List<String> getAllBranchNames();
    List<String> getDrinkTypesByCategory(String keyword);
    List<Drink> getAllDrinks();
    List<Branch> getAllBranches();

    // ✅ Add these methods
    Order getOrderById(int orderId);
    String getCustomerNameById(int customerId);
    boolean savePayment(int orderId, String method, String code);
}
