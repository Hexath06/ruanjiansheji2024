package Service;

import DAO.OrderItemMapper;
import Entity.OrderItem;
import Utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.math.BigDecimal;
import java.util.ArrayList;

public class OrderItemService {
    public ArrayList<OrderItem> getOrderItemsByOrderId(int orderId) {
        SqlSession session = null;
        ArrayList<OrderItem> orderItems = null;
        try {
            session = MyBatisUtil.getSession();
            OrderItemMapper orderItemMapper = session.getMapper(OrderItemMapper.class);
            orderItems = orderItemMapper.getOrderItemsByOrderId(orderId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return orderItems;
    }

    public void addOrderItem(int orderId, int userId, int productId, BigDecimal price, int quantity) {
        SqlSession session = null;
        try {
            session = MyBatisUtil.getSession();
            OrderItemMapper orderItemMapper = session.getMapper(OrderItemMapper.class);
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setUserId(userId);
            orderItem.setProductId(productId);
            orderItem.setPrice(price);
            orderItem.setQuantity(quantity);
            orderItemMapper.addOrderItem(orderItem);
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

}
