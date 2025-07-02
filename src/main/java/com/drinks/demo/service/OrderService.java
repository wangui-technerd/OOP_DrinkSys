package com.drinks.demo.service;

import com.drinks.demo.model.Order;
import com.drinks.demo.model.OrderDetail;

import java.util.List;

public interface OrderService {
    int placeOrder(Order order, List<OrderDetail> orderDetails);
}
