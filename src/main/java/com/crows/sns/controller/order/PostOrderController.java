package com.crows.sns.controller.order;

import com.crows.sns.mapper.OrderMapper;
import com.crows.sns.mapper.UserMapper;
import com.crows.sns.pojo.Order;
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
    @Autowired
    private UserMapper userMapper;

    @PostMapping()
    public Order postOrder(@RequestBody Order order) {
        //先在user表中获取id
        int userId = userMapper.findUserIdByNickname(order.getNickname());
        //修改order的userId
        order.setUser_id(userId);
        //插入order
        orderMapper.insertOrder(order);
        return order;
    }
}
