package DAO;

import Entity.ShopCart;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface CartMapper {
    @Select("SELECT * FROM shopcart WHERE userid = #{userId}")
    ArrayList<ShopCart> getAllCart(@Param("userId") int userId);

    @Select("SELECT * FROM shopcart WHERE userid = #{userId} AND productid = #{productId}")
    ShopCart getShopCartByUserAndProduct(@Param("userId") int userId, @Param("productId") int productId);

    @Insert("INSERT INTO shopcart (userid, productid, quantity, total) " +
            "VALUES (#{shopCart.userId}, #{shopCart.productId}, #{shopCart.quantity}, #{shopCart.total})")
    void addProductToCart(@Param("shopCart") ShopCart shopCart);

    @Update("UPDATE shopcart SET quantity = #{quantity}, total = #{total} " +
            "WHERE userid = #{userId} AND productid = #{productId}")
    void updateShopCart(@Param("userId") int userId, @Param("productId") int productId,
                        @Param("quantity") int quantity, @Param("total") BigDecimal total);

    @Delete("DELETE FROM shopcart WHERE userid = #{userId}")
    void clearCart(@Param("userId") int userId);

    @Update("UPDATE shopcart SET quantity = #{defaultQuantity} WHERE userid = #{userId}")
    void resetQuantityToDefault(@Param("userId") int userId, @Param("defaultQuantity") int defaultQuantity);

    @Delete("DELETE FROM shopcart WHERE userid = #{userId} AND productid = #{productId}")
    void removeCartItem(@Param("userId") int userId, @Param("productId") int productId);
}