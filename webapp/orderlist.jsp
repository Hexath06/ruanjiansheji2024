<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Service.OrderService" %>
<%@ page import="Entity.Order" %>
<%@ page import="Entity.User" %>

<%
    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    OrderService orderService = new OrderService();
    List<Order> orders = orderService.getOrdersByUserId(currentUser.getId());
%>

<!DOCTYPE html>
<html>
<head>
    <title>Order List</title>
    <link href="CSS/orderlist.css" rel="stylesheet" type="text/css">
    <script src="https://kit.fontawesome.com/f0c671614b.js" crossorigin="anonymous"></script>
    <script src="JS/jQuery.js"></script>
    <script>
        function viewReceipt(orderId) {
            window.location.href = 'receipt.jsp?orderId=' + orderId;
        }
    </script>
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

<h1>Order List</h1>
<table>
    <tr>
        <th>Order ID</th>
        <th>Total</th>
        <th>Created Time</th>
        <th>Action</th>
    </tr>
    <% for (Order order : orders) { %>
    <tr>
        <td><%= order.getId() %></td>
        <td><%= order.getTotal() %></td>
        <td><%= order.getCreatedTime() %></td>
        <td><button onclick="viewReceipt(<%= order.getId() %>)">View Receipt</button></td>
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
<script src="JS/orderlist.js"></script>
</body>
</html>
