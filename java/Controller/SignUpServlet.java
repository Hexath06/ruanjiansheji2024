package Controller;

import Entity.User;
import Service.UserService;
import com.google.gson.JsonObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SignUpServlet", value = "/register")
public class SignUpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Not used
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        JsonObject jsonResponse = new JsonObject();

        try {
            String userName = request.getParameter("register-username");
            String password = request.getParameter("register-password");
            String email = request.getParameter("register-email");

            if (UserService.isUsernameExists(userName)) {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Username already exists");
            }
            else {
                User user = new User();
                user.setUsername(userName);
                user.setPassword(password);
                user.setEmail(email);
                UserService.signUp(user);
                jsonResponse.addProperty("success", true);
            }

            response.getWriter().write(jsonResponse.toString());

        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "An error occurred during sign-up");
            response.getWriter().write(jsonResponse.toString());
        }
    }
}
