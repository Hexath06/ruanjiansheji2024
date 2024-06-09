package Controller;

import Entity.Banner;
import Entity.Category;
import Entity.User;
import Service.BannerService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "RedirectServlet", value = "/redirectToHome")
public class RedirectServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            retrieveBannerData(request);
            retrieveCategoryData(request);
            request.getRequestDispatcher("homepage.jsp").forward(request, response);
        } else {
            response.sendRedirect("main.jsp");
        }
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
