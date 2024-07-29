package com.crows.sns.service.Impl;

import com.crows.sns.mapper.OrderMapper;
import com.crows.sns.mapper.UserMapper;
import com.crows.sns.pojo.Order;
import com.crows.sns.pojo.User;
import com.crows.sns.service.EditOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EditOrderServiceImpl implements EditOrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Order editOrder(Order order) {
        //首先直接更新订单信息
        orderMapper.updateOrder(order);
        //然后对关联表进行更改
        //建立竞标者id的列表，并进行添加
        List<Integer> bidderIds = new ArrayList<>();
        //通过bidders获取ids
        for (String bidder : order.getBidders()) {
            bidderIds.add(userMapper.findUserIdByNickname(bidder));
        }
        //删除原有的关联关系
        orderMapper.deleteUserIdsByOrderId(order.getOrders_id());

        //添加新的关联关系
        for (Integer bidderId : bidderIds) {
            orderMapper.insertOrderIdAndUserId(order.getOrders_id(), bidderId);
        }
        return order;
    }
}
