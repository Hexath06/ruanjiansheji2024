package Controller;

import Entity.Product;
import Service.CartService;
import Service.ProductService;
import Service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ShowDetailServlet", value = "/detail")
public class ShowDetailServlet extends HttpServlet {
    private CartService cartService;
    private UserService userService;
    private ProductService productService;

    @Override
    public void init() {
        cartService = new CartService();
        userService = new UserService();
        productService = new ProductService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("showDetail".equals(action)) {
            doGet_showDetail(request, response);
        }
    }

    protected void doGet_showDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdStr = request.getParameter("productId");

        if (productIdStr != null && !productIdStr.trim().isEmpty()) {
            try {
                int productId = Integer.parseInt(productIdStr);

                Product retrievedProduct = productService.getProductById(productId);
                request.setAttribute("retrievedProduct", retrievedProduct);
                request.getRequestDispatcher("detail.jsp").forward(request, response);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
