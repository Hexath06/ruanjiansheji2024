package DAO;

import Entity.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

public interface OrderItemMapper {
    @Insert("INSERT INTO order_items (orderid, userid, productid, price, quantity) VALUES (#{orderId}, #{userId}, #{productId}, #{price}, #{quantity})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addOrderItem(OrderItem orderItem);

    @Select("SELECT * FROM order_items WHERE orderid = #{orderId}")
    ArrayList<OrderItem> getOrderItemsByOrderId(@Param("orderId") int orderId);

    @Select("SELECT * FROM order_items WHERE userid = #{userId}")
    ArrayList<OrderItem> getAllOrdersForUser(@Param("userId") int userId);
}
