package com.crows.sns.controller;

import com.crows.sns.pojo.Order;
import com.crows.sns.service.Impl.EditOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class EditOrderController {
    @Autowired
    private EditOrderServiceImpl editOrderService;

    @PutMapping("/{id}")
    public Order editOrder(@RequestBody Order request,@PathVariable int id)
    {
        //首先封装好id
        request.setOrders_id(id);
        //调用Service层的editOrder方法
        return editOrderService.editOrder(request);
    }
}
