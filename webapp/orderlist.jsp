<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Service.OrderService" %>
<%@ page import="Entity.Order" %>
<%@ page import="Entity.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
        var isLoggedIn = ${not empty sessionScope.user}; // Pass logged-in status as a JavaScript variable

        function viewReceipt(orderId) {
            window.location.href = '${pageContext.request.contextPath}/receipt?orderId=' + orderId;
        }
    </script>
</head>
<body>
<header>
    <div id="header-left">
        <img src="Images/Asset/logo.jpg" alt="Logo">
    </div>
    <form id="header-mid" action="searchProducts" method="get">
        <div id="search-container">
            <span class="fa fa-search"></span>
            <input type="text" placeholder="Search" id="search" name="query" class="search-input">
            <select id="filter-category" name="category" class="search-select">
                <option value="">All Categories</option>
                <c:forEach var="category" items="${categoryList}">
                    <option value="${category.name}">${category.name}</option>
                </c:forEach>
            </select>
            <button type="submit" id="search-button" class="search-button"></button>
        </div>
    </form>
    <div id="header-right">
        <div id="hello">Hello,
            <span id="active-username">${sessionScope.user.username}</span>
            <div id="dropdown-menu">
                <span onclick="window.location.href='UpdateBio'">User Profile</span>
                <span onclick="window.location.href='orderlist'">View Orders</span>
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
