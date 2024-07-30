package com.crows.sns.controller.order;

import com.crows.sns.mapper.OrderMapper;
import com.crows.sns.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class DeleteOrderController {
    @Autowired
    private OrderMapper orderMapper;

    @DeleteMapping("/{id}")
    public Response deleteOrder(@PathVariable int id)
    {
        orderMapper.deleteOrderById(id);
        return new Response(true, "delete order success");
    }
}
