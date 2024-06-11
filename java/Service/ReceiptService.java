package Service;

import DAO.OrderItemMapper;
import DAO.ReceiptMapper;
import Entity.Order;
import Entity.OrderItem;
import Utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;

public class ReceiptService {
    public static Order getOrder(int orderId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            ReceiptMapper receiptMapper = session.getMapper(ReceiptMapper.class);
            return receiptMapper.getOrderById(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<OrderItem> getOrderItems(int orderId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            ReceiptMapper receiptMapper = session.getMapper(ReceiptMapper.class);
            return receiptMapper.getOrderItemsByOrderId(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<OrderItem> getAllOrdersForUser(int userId) {
        SqlSession session = null;
        ArrayList<OrderItem> orders = null;
        try {
            session = MyBatisUtil.getSession();
            OrderItemMapper orderItemMapper = session.getMapper(OrderItemMapper.class);
            orders = orderItemMapper.getAllOrdersForUser(userId);
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
