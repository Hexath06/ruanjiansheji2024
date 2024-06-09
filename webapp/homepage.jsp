<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Homepage</title>
    <script src="https://kit.fontawesome.com/f0c671614b.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="CSS/homepage.css">
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
                <span onclick="window.location.href='logout'" style="color: red; font-weight: 600">Logout</span>
            </div>
        </div>
        <span id="cart" class="fa fa-shopping-cart"></span>
    </div>
</header>

<main>
    <%-- carousel --%>
    <div id="carousel-container">
        <c:forEach var="banner" items="${bannerList}">
            <div class="carousel-image">
                <a href="${banner.link}"><img src="${banner.image}" alt="Banner Image"></a>
            </div>
        </c:forEach>
        <div id="change-banner">
            <span id="prev">&#10094;</span>
            <span id="next">&#10095;</span>
        </div>
        <div id="dot-container"></div>
    </div>
    <%-- carousel --%>

    <%-- category container --%>
    <div id="category-container">
        <h2>Category</h2>
        <div id="category-body">
            <c:forEach var="category" items="${categoryList}">
                <div class="category-image">
                    <a href="product?category=${category.name}"><img src="${category.image}" alt="Category Image"></a>
                </div>
            </c:forEach>
        </div>
    </div>
    <%-- category container --%>
</main>

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
            <img src="Images/Asset/footer_right1.png" alt="Footer Right Logo">
        </div>
        <ul id="to-download">
            <li><img src="Images/Asset/icon-download-android.png" alt="Download Android"></li>
            <li><img src="Images/Asset/icon-download-ios.png" alt="Download iOS"></li>
            <li><img src="Images/Asset/icon-download-huawei.png" alt="Download Huawei"></li>
        </ul>
    </div>
</footer>
<script src="JS/homepage.js"></script>
</body>
</html>
