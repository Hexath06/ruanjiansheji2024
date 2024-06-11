package Service;

import DAO.CartMapper;
import DAO.ProductMapper;
import Entity.*;
import Utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import java.math.BigDecimal;

import java.sql.SQLException;
import java.util.ArrayList;

public class CartService {
    public static ArrayList<ShopCart> getCart(int userId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            CartMapper cartMapper = session.getMapper(CartMapper.class);
            return cartMapper.getAllCart(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void addProductToCart(Product product, User user, int quantity) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSession()) {
            ProductMapper productMapper = session.getMapper(ProductMapper.class);
            CartMapper cartMapper = session.getMapper(CartMapper.class);

            Product existingProduct = productMapper.getProductById(product.getId());
            if (existingProduct != null && existingProduct.getQuantity() > 0) {
                BigDecimal price = existingProduct.getPrice();

                ShopCart existingCartItem = cartMapper.getShopCartByUserAndProduct(user.getId(), product.getId());
                if (existingCartItem != null) {
                    int newCartQuantity = existingCartItem.getQuantity() + quantity;
                    if (newCartQuantity > existingProduct.getQuantity()) {
                        throw new IllegalStateException("Cannot add more than available stock.");
                    }
                    BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(newCartQuantity));
                    existingCartItem.setQuantity(newCartQuantity);
                    existingCartItem.setTotal(totalPrice);
                    cartMapper.updateShopCart(existingCartItem.getUserId(), existingCartItem.getProductId(), newCartQuantity, totalPrice);
                } else {
                    if (quantity > existingProduct.getQuantity()) {
                        throw new IllegalStateException("Cannot add more than available stock.");
                    }
                    BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));
                    ShopCart newCartItem = new ShopCart();
                    newCartItem.setUserId(user.getId());
                    newCartItem.setProductId(product.getId());
                    newCartItem.setQuantity(quantity);
                    newCartItem.setTotal(totalPrice);
                    cartMapper.addProductToCart(newCartItem);
                }
                session.commit();
            } else {
                throw new IllegalStateException("Product does not exist or quantity is insufficient.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Rethrow to propagate the error
        }
    }

    public static void updateProductQuantity(int userId, int productId, int quantity) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            CartMapper cartMapper = session.getMapper(CartMapper.class);
            ProductMapper productMapper = session.getMapper(ProductMapper.class);

            Product product = productMapper.getProductById(productId);
            if (product != null && quantity <= product.getQuantity()) {
                BigDecimal price = product.getPrice();
                BigDecimal total = price.multiply(BigDecimal.valueOf(quantity));
                cartMapper.updateShopCart(userId, productId, quantity, total);
                session.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearCart(int userId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            CartMapper cartMapper = session.getMapper(CartMapper.class);
            cartMapper.clearCart(userId);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetQuantityToDefault(int userId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            CartMapper cartMapper = session.getMapper(CartMapper.class);
            // Assuming you have a default quantity value stored somewhere
            int defaultQuantity = 1;
            cartMapper.resetQuantityToDefault(userId, defaultQuantity);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeCartItem(int userId, int productId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            CartMapper cartMapper = session.getMapper(CartMapper.class);
            cartMapper.removeCartItem(userId, productId);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
