package Controller;

import Entity.Category;
import Entity.Order;
import Entity.User;
import Service.BannerService;
import Service.OrderItemService;
import Service.OrderService;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "OrderListServlet", value = "/orderlist")
public class OrderListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = new OrderService();
        OrderItemService orderItemService = new OrderItemService();

        // Retrieve user ID from session
        int userId = ((User)request.getSession().getAttribute("user")).getId();

        ArrayList<Order> orders = orderService.getOrdersByUserId(userId);

        retrieveCategoryData(request);
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("orderlist.jsp").forward(request, response);
    }

    private void retrieveCategoryData(HttpServletRequest request) {
        ArrayList<Category> categoryList = BannerService.getCategory();
        request.setAttribute("categoryList", categoryList);
    }
}
