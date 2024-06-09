<%@ page import="Entity.UserProfile" %>
<%@ page import="Service.ProfileService" %>
<%@ page import="Entity.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Update Profile</title>
    <link rel="stylesheet" href="CSS/updateProfile.css">
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

<%
    User user = (User) session.getAttribute("user");
    if (user != null) {
        UserProfile userProfile = ProfileService.getUserProfileById(user.getId());
        if (userProfile != null) {
            request.setAttribute("currentUserProfile", userProfile);
        }
    }
%>

<div id="updateProfile-container">
    <div id="updateProfile-left">
        <img src="${currentUserProfile.image}" id="profileImage">

        <form id="addImageForm" action="AddImageServlet" method="post" enctype="multipart/form-data">
            <input name="username" value="${sessionScope.user.username}" readonly hidden="hidden">
            <input type="file" name="image" id="imageInput" >
            <input type="submit" value="Add Image" style="display:none;">
        </form>
    </div>
    <div id="updateProfile-right">
        <h1>User Bio</h1>
        <form id="updateBioForm" action="UpdateBio" method="post">
            <label for="username">Username&nbsp;:&ensp;</label>
            <input type="text" name="username" value="${sessionScope.user.username}" id="username">
            <label for="dob">Date of Birth&nbsp;:&ensp;</label>
            <input type="date" name="dob" value="${sessionScope.user.dob}" id="dob">
            <label for="phoneNum">Phone&nbsp;:&ensp;</label>
            <input type="text" name="phoneNum" value="${sessionScope.user.phoneNum}" id="phoneNum">
            <label for="email">Email&nbsp;:&ensp;</label>
            <input type="email" name="email" value="${sessionScope.user.email}" id="email">
            <input type="submit" value="Save Change">
        </form>
    </div>
</div>

<% if (request.getAttribute("successMessage") != null) { %>
<script>
    var successMessage = "<%= request.getAttribute("successMessage") %>";
</script>
<% } else { %>
<script>
    var successMessage = null;
</script>
<% } %>

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
<script src="JS/updateProfile.js"></script>
</body>
</html>
