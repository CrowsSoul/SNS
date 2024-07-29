package com.crows.sns.controller;

import com.crows.sns.mapper.OrderMapper;
import com.crows.sns.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class ApproveOrderController {
    @Autowired
    private OrderMapper orderMapper;

    @PutMapping("/{id}/approve")
    public Response approveOrder(@PathVariable int id)
    {
        orderMapper.approveOrder(id);
        return new Response(true, "Order approved successfully.");
    }
}
