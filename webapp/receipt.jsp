<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Service.OrderItemService" %>
<%@ page import="Entity.OrderItem" %>
<%@ page import="Entity.Product" %>
<%@ page import="Service.ProductService" %>

<%
    String orderIdParam = request.getParameter("orderId");
    if (orderIdParam == null || orderIdParam.isEmpty()) {
        response.sendRedirect("orderlist.jsp");
        return;
    }

    int orderId = Integer.parseInt(orderIdParam);

    OrderItemService orderItemService = new OrderItemService();
    ArrayList<OrderItem> orderItems = orderItemService.getOrderItemsByOrderId(orderId);
%>

<!DOCTYPE html>
<html>
<head>
    <title>Order Receipt</title>
    <link href="CSS/receipt.css" rel="stylesheet" type="text/css">
    <script src="https://kit.fontawesome.com/f0c671614b.js" crossorigin="anonymous"></script>
    <script src="JS/jQuery.js"></script>
    <script>
        var isLoggedIn = ${not empty sessionScope.user}; // Pass logged-in status as a JavaScript variable
    </script>
</head>
<body>
<header>
    <div id="header-left">
        <img src="Images/Asset/logo.jpg" alt="Logo">
    </div>
    <form id="header-mid">
        <div id="search-container">
            <label for="search"><span class="fa fa-search"></span></label>
            <input type="text" placeholder="Search" id="search">
        </div>
    </form>
    <div id="header-right">
        <div id="hello">Hello,
            <span id="active-username">${sessionScope.user.username}</span>
            <div id="dropdown-menu">
                <span onclick="window.location.href='updateProfile.jsp'">User Profile</span>
                <span onclick="window.location.href='orderlist.jsp'">View Orders</span>
                <span onclick="window.location.href='logout'" style="color: red">Logout</span>
            </div>
        </div>
        <span id="cart" class="fa fa-shopping-cart"></span>
    </div>
</header>

<h1>Order Receipt</h1>
<table>
    <tr>
        <th>Product ID</th>
        <th>Price</th>
        <th>Quantity</th>
    </tr>
    <% for (OrderItem item : orderItems) { %>
    <tr>
        <%
            Product product = ProductService.getProductById(item.getProductId());
        %>
        <td><%= product.getName() %></td>
        <td><%= item.getPrice() %></td>
        <td><%= item.getQuantity() %></td>
    </tr>
    <% } %>
</table>

<footer>
    <div id="footer-tokopedia">
        <h2>Tokopedia</h2>
        <ul>
            <li>About Us</li>
            <li>Career</li>
            <li>Blog</li>
        </ul>
    </div>
    <div id="footer-support">
        <h2>Tokopedia</h2>
        <ul>
            <li>Tokopedia Care</li>
            <li>Terms and Condition</li>
            <li>Customer Support</li>
        </ul>
    </div>
    <div id="footer-right">
        <div id="footer-right-logo">
            <img src="Images/Asset/footer_right1.png">
        </div>
        <ul id="to-download">
            <li><img src="Images/Asset/icon-download-android.png"></li>
            <li><img src="Images/Asset/icon-download-ios.png"></li>
            <li><img src="Images/Asset/icon-download-huawei.png"></li>
        </ul>
    </div>
</footer>
<script src="JS/receipt.js"></script>
</body>
</html>
