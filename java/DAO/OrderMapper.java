package DAO;

import Entity.Order;
import Entity.PurchasedProduct;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface OrderMapper {

    @Insert("INSERT INTO orders (userid, total) VALUES (#{userId}, #{total})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int createOrder(Order order);


    @Update("UPDATE orders SET total = #{total} WHERE id = #{orderId}")
    void updateOrderTotal(@Param("orderId") int orderId, @Param("total") BigDecimal total);

    @Select("SELECT p.id, p.name, p.image, oi.quantity, oi.price, (oi.quantity * oi.price) AS totalPrice " +
            "FROM order_items oi " +
            "JOIN products p ON oi.productid = p.id " +
            "WHERE oi.orderid = #{orderId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "productName", column = "name"),
            @Result(property = "image", column = "image"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "productPrice", column = "price"),
            @Result(property = "totalPrice", column = "totalPrice")
    })
    ArrayList<PurchasedProduct> getOrderDetails(@Param("orderId") int orderId);

    @Select("SELECT * FROM orders WHERE userid = #{userId}")
    ArrayList<Order> getOrdersByUserId(@Param("userId") int userId);

    @Select("SELECT * FROM orders")
    ArrayList<Order> getAllOrders();
}
