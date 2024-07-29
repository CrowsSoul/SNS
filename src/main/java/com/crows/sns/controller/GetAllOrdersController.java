package com.crows.sns.controller;

import com.crows.sns.pojo.Order;
import com.crows.sns.pojo.OrderList;
import com.crows.sns.service.Impl.GetAllOrdersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class GetAllOrdersController {
    @Autowired
    private GetAllOrdersServiceImpl getAllOrdersService;

    @GetMapping
    public OrderList getAllOrders()
    {
        OrderList response = new OrderList();
        List<Order> orders = getAllOrdersService.getAllOrders();
        response.setOrders(orders);
        return response;
    }
}
