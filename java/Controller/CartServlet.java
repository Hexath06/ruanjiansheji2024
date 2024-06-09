package Controller;

import Entity.*;
import Service.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "CartServlet", value = "/cart")
public class CartServlet extends HttpServlet {
    private CartService cartService;
    private UserService userService;
    private ProductService productService;
    private OrderItemService orderItemService;
    private OrderService orderService;

    @Override
    public void init() {
        cartService = new CartService();
        userService = new UserService();
        productService = new ProductService();
        orderItemService = new OrderItemService();
        orderService = new OrderService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("showCart".equals(action)) {
            doGet_showCart(request, response);
        }
    }

    protected void doGet_showCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect("main.jsp");
            return;
        }
        int userId = currentUser.getId();

        ArrayList<ShopCart> cartItems = cartService.getCart(userId);
        ArrayList<RetrievedProduct> retrievedProductArrayList = new ArrayList<>();

        for (ShopCart item : cartItems) {
            Product product = productService.getProductById(item.getProductId());
            if (product != null) {
                retrievedProductArrayList.add(new RetrievedProduct(product.getImage(), product.getName(), product.getPrice()));
            }
        }

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("retrievedProductArrayList", retrievedProductArrayList);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonString = sb.toString();
        JSONObject jsonObject = new JSONObject(jsonString);
        String action = jsonObject.getString("action");

        if ("addToCart".equals(action)) {
            doPost_addToCart(jsonObject, request, response);
        } else if ("updateQuantity".equals(action)) {
            doPost_updateQuantity(jsonObject, request, response);
        } else if ("removeCart".equals(action)) {
            doPost_removeCart(jsonObject, request, response);
        } else if ("purchase".equals(action)) {
            try {
                doPost_purchase(jsonObject, request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void doPost_addToCart(JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = jsonObject.getString("username");
        int productId = jsonObject.getInt("productId");
        int quantity = jsonObject.getInt("quantity");

        Product product = productService.getProductById(productId);
        User user = userService.getUserByName(username);

        if (product == null || user == null) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Product or user not found\"}");
            return;
        }

        try {
            cartService.addProductToCart(product, user, quantity);
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"status\":\"success\",\"message\":\"Product added to cart successfully\"}");
        } catch (IllegalStateException e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"An unexpected error occurred.\"}");
        }
    }

    protected void doPost_updateQuantity(JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = jsonObject.getInt("productId");
        int quantity = jsonObject.getInt("quantity");
        User currentUser = (User) request.getSession().getAttribute("user");

        if (currentUser != null) {
            int userId = currentUser.getId();
            Product product = productService.getProductById(productId);

            if (product != null && quantity <= product.getQuantity()) {
                cartService.updateProductQuantity(userId, productId, quantity);
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"status\":\"success\",\"availableStock\":" + product.getQuantity() + "}");
            } else {
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"status\":\"error\",\"message\":\"Quantity exceeds available stock\",\"availableStock\":" + product.getQuantity() + "}");
            }
        } else {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"User not found\"}");
        }
    }

    protected void doPost_removeCart(JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");

        if (currentUser != null) {
            int userId = currentUser.getId();
            cartService.clearCart(userId);

            // Reset quantity to default value (1) after clearing the cart
            cartService.resetQuantityToDefault(userId);

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"status\":\"success\"}");
        } else {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"User not found\"}");
        }
    }

    protected void doPost_purchase(JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        JSONArray selectedItems = jsonObject.getJSONArray("selectedItems");
        User currentUser = (User) request.getSession().getAttribute("user");

        if (currentUser == null) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"User not found\"}");
            return;
        }

        int userId = currentUser.getId();
        BigDecimal total = BigDecimal.ZERO;

        // Create an order and get the order ID
        int orderId = orderService.createOrder(userId, total);

        // Flag to check if all products are available
        boolean allAvailable = true;
        for (int i = 0; i < selectedItems.length(); i++) {
            JSONObject item = selectedItems.getJSONObject(i);
            int productId = item.getInt("productId");
            int quantity = item.getInt("quantity");

            Product product = productService.getProductById(productId);
            if (product == null || product.getQuantity() < quantity) {
                allAvailable = false;
                break;
            }
        }

        if (!allAvailable) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Insufficient stock for one or more items\"}");
            return;
        }

        for (int i = 0; i < selectedItems.length(); i++) {
            JSONObject item = selectedItems.getJSONObject(i);
            int productId = item.getInt("productId");
            BigDecimal price = item.getBigDecimal("price");
            int quantity = item.getInt("quantity");

            orderItemService.addOrderItem(orderId, userId, productId, price, quantity);

            // Deduct stock
            productService.deductStock(productId, quantity);

            // Accumulate the total price
            total = total.add(price.multiply(new BigDecimal(quantity)));
        }

        System.out.println("Total price calculated: " + total);

        // Update the total price of the order
        orderService.updateOrderTotal(orderId, total);
        System.out.println("Order ID: " + orderId + ", Updated Total: " + total);

        // Clear the selected items from the cart
        for (int i = 0; i < selectedItems.length(); i++) {
            JSONObject item = selectedItems.getJSONObject(i);
            int productId = item.getInt("productId");
            cartService.removeCartItem(userId, productId);
        }

        System.out.println("New Order Created: Order ID = " + orderId);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("{\"status\":\"success\", \"orderId\": " + orderId + "}");
    }



}
