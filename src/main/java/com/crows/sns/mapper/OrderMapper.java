package com.crows.sns.mapper;

import com.crows.sns.pojo.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Select("SELECT * FROM orders")
    public List<Order> getAllOrders();

    @Select("SELECT user_id FROM user_order WHERE orders_id = #{orderId}")
    public List<Integer> getUserIdsByOrderId(int orderId);

    @Select("SELECT * FROM orders WHERE orders_id = #{orderId}")
    public Order getOrderById(int orderId);

    @Insert("INSERT INTO user_order (orders_id, user_id) VALUES (#{orderId}, #{userId})")
    public void insertOrderIdAndUserId(int orderId, int userId);

    @Delete("DELETE FROM orders WHERE orders_id = #{orderId}")
    public void deleteOrderById(int orderId);

    @Insert("INSERT INTO orders(orders_id, user_id, " +
            "nickname, orders_name, " +
            "orders_price, orders_introduction, " +
            "orders_status, created_time, successful_bidder) VALUES " +
            "(#{orders_id}, #{user_id}, #{nickname}, #{orders_name}, " +
            "#{orders_price}, #{orders_introduction}, " +
            "#{orders_status}, #{created_time}, #{successful_bidder})")
    public void insertOrder(Order order);

    @Update("UPDATE orders SET " +
            "nickname = #{nickname}, orders_name = #{orders_name}, " +
            "orders_price = #{orders_price}, orders_introduction = #{orders_introduction}, " +
            "orders_status = #{orders_status}, created_time = #{created_time}, " +
            "successful_bidder = #{successful_bidder} WHERE orders_id = #{orders_id}")
    public void updateOrder(Order order);

    @Delete("DELETE FROM user_order WHERE orders_id = #{orderId}")
    public void deleteUserIdsByOrderId(int orderId);
}
