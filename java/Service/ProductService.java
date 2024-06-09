package Service;

import DAO.ProductMapper;
import Entity.Product;
import Utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductService {
    public static ArrayList<Product> getProduct(String category) {
        ArrayList<Product> product_arrayList = null;
        SqlSession session = null;

        try {
            session = MyBatisUtil.getSession();
            ProductMapper productMapper = session.getMapper(ProductMapper.class);
            product_arrayList = productMapper.getProductByCategory(category);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return product_arrayList;
    }

    public static Product getProductById(int productId) {
        SqlSession session = null;

        try {
            session = MyBatisUtil.getSession();
            ProductMapper productMapper = session.getMapper(ProductMapper.class);
            return productMapper.getProductById(productId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public void deductStock(int productId, int quantity) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSession()) {
            ProductMapper productMapper = session.getMapper(ProductMapper.class);
            Product product = productMapper.getProductById(productId);
            if (product != null && product.getQuantity() >= quantity) {
                product.setQuantity(product.getQuantity() - quantity);
                productMapper.updateProduct(product);
                session.commit();
            } else {
                throw new IllegalStateException("Insufficient stock for product ID: " + productId);
            }
        }
    }
}
