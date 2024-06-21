<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Products</title>
    <script src="https://kit.fontawesome.com/f0c671614b.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="CSS/product.css">
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

<div id="product-container">
    <c:forEach var="product" items="${productList}">
        <div class="product-item">
            <img src="${product.image}" alt="${product.name}">
            <span class="product-name">${product.name}</span>
            <span class="product-price">$&nbsp;${product.price}</span>
            <input type="hidden" class="product-id" value="${product.id}">
        </div>
    </c:forEach>
</div>

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
        <h2>Support</h2>
        <ul>
            <li>Tokopedia Care</li>
            <li>Terms and Conditions</li>
            <li>Customer Support</li>
        </ul>
    </div>
    <div id="footer-right">
        <div id="footer-right-logo">
            <img src="Images/Asset/footer_right1.png" alt="Footer Logo">
        </div>
        <ul id="to-download">
            <li><img src="Images/Asset/icon-download-android.png" alt="Download Android"></li>
            <li><img src="Images/Asset/icon-download-ios.png" alt="Download iOS"></li>
            <li><img src="Images/Asset/icon-download-huawei.png" alt="Download Huawei"></li>
        </ul>
    </div>
</footer>
<script src="JS/product.js"></script>
</body>
</html>
