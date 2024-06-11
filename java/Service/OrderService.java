package Service;

import DAO.OrderMapper;
import Entity.Order;
import Entity.PurchasedProduct;
import Utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.math.BigDecimal;
import java.util.ArrayList;

public class OrderService {
    public ArrayList<Order> getAllOrders() {
        SqlSession session = null;
        ArrayList<Order> orders = null;
        try {
            session = MyBatisUtil.getSession();
            OrderMapper orderMapper = session.getMapper(OrderMapper.class);
            orders = orderMapper.getAllOrders();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return orders;
    }

    public int createOrder(int userId, BigDecimal total) {
        SqlSession session = null;
        try {
            session = MyBatisUtil.getSession();
            OrderMapper orderMapper = session.getMapper(OrderMapper.class);
            Order order = new Order();
            order.setUserId(userId);
            order.setTotal(total);
            orderMapper.createOrder(order);
            session.commit();
            return order.getId(); // Ensure the order ID is being retrieved and returned
        } catch (Exception e) {
            if (session != null) {
                session.rollback();
            }
            e.printStackTrace();
            return -1; // Or handle the error appropriately
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void updateOrderTotal(int orderId, BigDecimal total) {
        SqlSession session = null;
        try {
            session = MyBatisUtil.getSession();
            OrderMapper orderMapper = session.getMapper(OrderMapper.class);
            orderMapper.updateOrderTotal(orderId, total);
            session.commit();
        } catch (Exception e) {
            if (session != null) {
                session.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public ArrayList<PurchasedProduct> getOrderDetails(int orderId) {
        SqlSession session = null;
        ArrayList<PurchasedProduct> purchasedProducts = new ArrayList<>();
        try {
            session = MyBatisUtil.getSession();
            OrderMapper orderMapper = session.getMapper(OrderMapper.class);
            purchasedProducts = orderMapper.getOrderDetails(orderId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return purchasedProducts;
    }

    public ArrayList<Order> getOrdersByUserId(int userId) {
        SqlSession session = null;
        ArrayList<Order> orders = null;
        try {
            session = MyBatisUtil.getSession();
            OrderMapper orderMapper = session.getMapper(OrderMapper.class);
            orders = orderMapper.getOrdersByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return orders;
    }
}
