package com.crows.sns.service.Impl;

import com.crows.sns.mapper.OrderMapper;
import com.crows.sns.mapper.UserMapper;
import com.crows.sns.pojo.Order;
import com.crows.sns.service.GetOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetOrderServiceImpl implements GetOrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public List<Order> getAllOrders() {
        //首先获取所有订单的信息
        List<Order> orders = orderMapper.getAllOrders();
        //然后获取订单对应的所有用户的信息
        for(Order order:orders)
        {
            int orderId = order.getOrders_id();
            //根据订单id获取所有对应的竞标者id
            List<Integer> bidderIds = orderMapper.getUserIdsByOrderId(orderId);
            //根据竞标者id获取所有对应的用户nickname
            List<String> nicknames = new ArrayList<String>();
            //遍历竞标者id，获取其nickname并加入列表
            for(Integer bidderId:bidderIds)
            {
                String nickname = userMapper.findNicknameById(bidderId);
                nicknames.add(nickname);
            }
            //将nickname列表加入到订单对象中
            order.setBidders(nicknames);

        }
        return orders;
    }

    @Override
    public Order getOneOrder(int orderId) {
        //根据订单id获取order
        Order order = orderMapper.getOrderById(orderId);
        //根据订单id获取所有对应的竞标者id
        List<Integer> bidderIds = orderMapper.getUserIdsByOrderId(orderId);
        //根据竞标者id获取所有对应的用户nickname
        List<String> nicknames = new ArrayList<String>();
        //遍历竞标者id，获取其nickname并加入列表
        for(Integer bidderId:bidderIds)
        {
            String nickname = userMapper.findNicknameById(bidderId);
            nicknames.add(nickname);
        }
        order.setBidders(nicknames);
        return order;
    }
}
