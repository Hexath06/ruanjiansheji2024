<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login Page</title>
  <link rel="stylesheet" href="CSS/main.css">
  <script src="JS/jQuery.js"></script>
</head>
<body>
<header>
    <img src="Images/Asset/logo.jpg">
</header>
<div id="forms-body">
  <div id="forms-left">
  </div>
  <div id="forms-right">
    <div id="forms-header">
      <span id="sign-in-tab">Sign-In</span>
      <span id="sign-up-tab">Sign-Up</span>
    </div>

    <form action="login" method="get" id="form-login">
      <label for="login-username">Username</label>
      <input type="text" name="login-username" id="login-username">
      <label for="login-password">Password</label>
      <input type="password" name="login-password" id="login-password">
      <input type="submit" value="Submit">
    </form>

    <form action="register" method="post" id="form-register">
      <label for="register-username">Username</label>
      <input type="text" name="register-username" id="register-username">
      <label for="register-email">Email</label>
      <input type="text" name="register-email" id="register-email">
      <label for="register-password">Password</label>
      <input type="password" name="register-password" id="register-password">
      <input type="submit" value="Submit">
    </form>
  </div>
</div>
<script src="JS/main.js"></script>
</body>
</html>
