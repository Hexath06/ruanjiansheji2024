package DAO;

import Entity.Order;
import Entity.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

public interface ReceiptMapper {
    @Select("SELECT * FROM orders WHERE id = #{orderId}")
    Order getOrderById(@Param("orderId") int orderId);

    @Select("SELECT p.name AS productName, oi.quantity, oi.price, (oi.quantity * oi.price) AS total " +
            "FROM order_items oi " +
            "JOIN product p ON oi.productid = p.id " +
            "WHERE oi.orderid = #{orderId}")
    ArrayList<OrderItem> getOrderItemsByOrderId(@Param("orderId") int orderId);
}

