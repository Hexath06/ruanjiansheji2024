package DAO;

import Entity.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

public interface ProductMapper {
    @Select("SELECT * FROM product WHERE category = #{category}")
    public ArrayList<Product> getProductByCategory(@Param("category") String category);

    @Select("SELECT * FROM product WHERE id = #{productId}")
    public Product getProductById(@Param("productId") int id);

    @Select("SELECT * FROM shopcart WHERE productid = #{productId}")
    public Product checkExist(@Param("productId") int id);

    @Update("UPDATE product SET quantity = #{newQuantity} WHERE id = #{productId}")
    void updateProductQuantity(@Param("productId") int productId, @Param("newQuantity") int newQuantity);

    @Update("UPDATE product SET quantity = #{quantity} WHERE id = #{id}")
    void updateProduct(Product product);
}
