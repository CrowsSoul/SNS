package com.crows.sns.service;

import com.crows.sns.pojo.Order;

import java.util.List;

public interface GetOrderService {
    public List<Order> getAllOrders();

    public Order getOneOrder(int orderId);
}
