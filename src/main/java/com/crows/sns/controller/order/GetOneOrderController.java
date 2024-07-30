package com.crows.sns.controller.order;

import com.crows.sns.pojo.Order;
import com.crows.sns.service.Impl.GetOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class GetOneOrderController {
    @Autowired
    private GetOrderServiceImpl getOrderService;

    @GetMapping("/{id}")
    public Order getOneOrder(@PathVariable int id)
    {
        return getOrderService.getOneOrder(id);
    }
}
