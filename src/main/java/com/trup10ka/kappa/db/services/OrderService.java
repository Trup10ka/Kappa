package com.trup10ka.kappa.db.services;

import com.trup10ka.kappa.data.Order;

import java.util.List;

public interface OrderService
{
    int addOrder(Order order);

    boolean deleteOrderById(int orderId);

    boolean updateOrderById(int orderId);

    Order getOrderById(int orderId);

    Order getOrderByCustomerId(int customerId);

    List<Order> getAllOrders();
}
