package com.drinks.demo.service;

import com.drinks.demo.database.OrderDao;
import com.drinks.demo.database.OrderDetailDao;
import com.drinks.demo.model.Order;
import com.drinks.demo.model.OrderDetail;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDAO = new OrderDao();
    private final OrderDetailDao orderDetailDAO = new OrderDetailDao();

    @Override
    public int placeOrder(Order order, List<OrderDetail> orderDetails) {
        int orderId = orderDAO.addOrder(order);
        if (orderId > 0) {
            for (OrderDetail detail : orderDetails) {
                detail.setOrderId(orderId);
                orderDetailDAO.addOrderDetail(detail);
            }
        }
        return orderId;
    }
}
