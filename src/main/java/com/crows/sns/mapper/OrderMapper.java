package com.crows.sns.mapper;

import com.crows.sns.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Select("SELECT * FROM orders")
    public List<Order> getAllOrders();

    @Select("SELECT user_id FROM user_order WHERE orders_id = #{orderId}")
    public List<Integer> getUserIdsByOrderId(int orderId);
}
