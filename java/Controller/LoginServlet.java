package Controller;

import Entity.Banner;
import Entity.Category;
import Entity.User;
import Service.BannerService;
import Service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        UserService userService = new UserService();

        try {
            String username = request.getParameter("login-username");
            String password = request.getParameter("login-password");

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);

            boolean loginSuccess = userService.login(user);

            if (loginSuccess) {
                user = userService.getUserByName(user.getUsername());
                request.getSession().setAttribute("user", user);

                retrieveBannerData(request);
                retrieveCategoryData(request);
                request.getRequestDispatcher("homepage.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void retrieveBannerData(HttpServletRequest request) {
        ArrayList<Banner> bannerList = BannerService.getBanner();
        request.setAttribute("bannerList", bannerList);
    }

    private void retrieveCategoryData(HttpServletRequest request) {
        ArrayList<Category> categoryList = BannerService.getCategory();
        request.setAttribute("categoryList", categoryList);
    }
}