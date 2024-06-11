<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping Cart</title>
    <link rel="stylesheet" href="CSS/cart.css">
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

<div id="content">
    <div id="cartContainer" class="cart-container">
        <div id="cartContainer-left">
            <div id="cartContainer-left-header">
                <h1>Shopping Cart</h1>
                <div id="select-cart">
                    <span id="select-all" class="select-all">Select All</span>
                    <span id="unselect-all" class="unselect-all">Unselect All</span>
                    <span id="remove-cart" class="remove-cart">Remove</span>
                </div>
            </div>

            <div id="cart-item-container">
                <c:forEach var="item" items="${cartItems}" varStatus="status">
                    <ul class="cart-item">
                        <li class="product-checkbox"><input type="checkbox" class="item-checkbox"></li>
                        <li class="product-image"><img src="${retrievedProductArrayList[status.index].image}" alt="${productNameAndPriceList[status.index].productName}"></li>
                        <li class="product-name">${retrievedProductArrayList[status.index].productName}</li>
                        <li class="product-price">${retrievedProductArrayList[status.index].productPrice}</li>
                        <li class="total">${item.total}</li>
                        <li class="quantity">
                            <button class="product-min" data-itemid="${item.productId}">-</button>
                            <input value="${item.quantity}" type="text" data-original-val="${item.quantity}">
                            <button class="product-add" data-itemid="${item.productId}">+</button>
                        </li>
                    </ul>
                </c:forEach>
            </div>
        </div>
        <ul id="cartContainer-right">
            <li>
                <h3>Shopping Summary</h3>
            </li>
            <li>
                <span>Total&nbsp;:&nbsp;</span>
                <span id="all-total"></span>
            </li>
            <li>
                <button id="buy">Buy</button>
            </li>
        </ul>
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
        <h2>Tokopedia</h2>
        <ul>
            <li>Tokopedia Care</li>
            <li>Terms and Condition</li>
            <li>Customer Support</li>
        </ul>
    </div>
    <div id="footer-right">
        <div id="footer-right-logo">
            <img src="Images/Asset/footer_right1.png" alt="Footer Logo">
        </div>
        <ul id="to-download">
            <li><img src="Images/Asset/icon-download-android.png" alt="Download for Android"></li>
            <li><img src="Images/Asset/icon-download-ios.png" alt="Download for iOS"></li>
            <li><img src="Images/Asset/icon-download-huawei.png" alt="Download for Huawei"></li>
        </ul>
    </div>
</footer>
<script src="JS/cart.js"></script>
</body>
</html>
