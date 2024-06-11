package Controller;

import Entity.Category;
import Entity.Product;
import Service.BannerService;
import Service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ProductServlet", value = "/searchProducts")
public class ProductServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService(); // Initialize ProductService
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String category = request.getParameter("category");
        ArrayList<Product> list = null;

        if (query != null)
            list = productService.searchProducts(query, category);
        else
            list = productService.getProduct(category);


        retrieveCategoryData(request);
        request.setAttribute("productList", list);
        request.getRequestDispatcher("product.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    private void retrieveCategoryData(HttpServletRequest request) {
        ArrayList<Category> categoryList = BannerService.getCategory();
        request.setAttribute("categoryList", categoryList);
    }
}