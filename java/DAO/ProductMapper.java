package DAO;

import Entity.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;

public interface ProductMapper {
    @Select("SELECT * FROM product WHERE category = #{category}")
    public ArrayList<Product> getProductByCategory(@Param("category") String category);

    @Select("SELECT * FROM product WHERE id = #{productId}")
    public Product getProductById(@Param("productId") int id);

    @Update("UPDATE product SET quantity = #{quantity} WHERE id = #{id}")
    void updateProduct(Product product);

    @SelectProvider(type = SqlProvider.class, method = "searchProducts")
    ArrayList<Product> searchProducts(@Param("query") String query, @Param("category") String category);

    class SqlProvider {
        public String searchProducts(@Param("query") String query, @Param("category") String category) {
            return new SQL() {{
                SELECT("*");
                FROM("product");
                if (query != null && !query.isEmpty()) {
                    WHERE("name LIKE CONCAT('%', #{query}, '%')");
                    if (category != null && !category.isEmpty()) {
                        WHERE("category = #{category}");
                    }
                } else if (category != null && !category.isEmpty()) {
                    WHERE("category = #{category}");
                }
            }}.toString();
        }
    }
}
