package com.crows.sns.controller;

import com.crows.sns.mapper.OrderMapper;
import com.crows.sns.pojo.Order;
import com.crows.sns.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class PostOrderController {
    @Autowired
    private OrderMapper orderMapper;

    @PostMapping()
    public Response postOrder(@RequestBody Order order) {
        orderMapper.insertOrder(order);
        return new Response(true, "order created");
    }
}
