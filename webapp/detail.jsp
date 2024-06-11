<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Product Details</title>
    <script src="https://kit.fontawesome.com/f0c671614b.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="CSS/detail.css">
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

<div id="detail-container">
    <!-- Product details -->
    <input id="product-id" type="hidden" value="${retrievedProduct.id}">
    <div id="detail-container-left">
        <img src="${retrievedProduct.image}" alt="${retrievedProduct.name}">
    </div>
    <div id="detail-container-right">
        <div id="product-name">
            <h1>${retrievedProduct.name}</h1>
        </div>
        <div id="product-price">
            <h2>$&nbsp;${retrievedProduct.price}</h2>
        </div>
        <div id="product-desc">
            <span>${retrievedProduct.description}</span>
        </div>
    </div>
    <div id="addToCart-container">
        <h1>Add to Cart</h1>
        <div id="set-quantity">
            <div id="set-quantity-left">
                <button id="decrease-quantity">-</button>
                <input type="text" value="1" id="buy-quantity">
                <button id="increase-quantity">+</button>
            </div>
            <div id="set-quantity-right">
                <span>Stock&nbsp;:&nbsp;</span>&nbsp;
                <span id="stock">${retrievedProduct.quantity}</span>
            </div>
        </div>
        <div id="buy-option">
            <div id="addToCart"><span>Add to Cart</span></div>
            <div id="buynow"><span id="buyNowButton">Buy Now</span></div>
        </div>
        <div id="error-message" style="color: red; display: none;"></div>
    </div>
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
<script src="JS/detail.js"></script>
</body>
</html>
