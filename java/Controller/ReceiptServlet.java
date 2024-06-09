package Controller;

import Entity.Product;
import Entity.PurchasedProduct;
import Service.ProductService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ReceiptServlet", value = "/receipt")
public class ReceiptServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() {
        productService = new ProductService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray selectedItems = (JSONArray) request.getSession().getAttribute("selectedItems");

        if (selectedItems == null) {
            response.sendRedirect("cart.jsp"); // Redirect to cart if no items are found
            return;
        }

        ArrayList<PurchasedProduct> purchasedProducts = new ArrayList<>();
        for (int i = 0; i < selectedItems.length(); i++) {
            JSONObject item = selectedItems.getJSONObject(i);
            int productId = item.getInt("productId");
            int quantity = item.getInt("quantity");

            Product product = productService.getProductById(productId);
            if (product != null) {
                purchasedProducts.add(new PurchasedProduct(
                        product.getImage(),
                        product.getName(),
                        product.getPrice(),
                        quantity
                ));
            }
        }

        request.setAttribute("purchasedProducts", purchasedProducts);
        request.getRequestDispatcher("receipt.jsp").forward(request, response);
    }
}

