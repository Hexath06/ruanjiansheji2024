package Controller;

import Entity.Category;
import Entity.User;
import Service.BannerService;
import Service.ProfileService;
import Service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "UpdateBioServlet", value = "/UpdateBio")
public class UpdateBioServlet extends HttpServlet {
    private UserService userService;
    private ProfileService profileService;

    @Override
    public void init() throws ServletException {
        userService = new UserService(); // Initialize UserService
        profileService = new ProfileService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        retrieveCategoryData(request);
        request.getRequestDispatcher("updateProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usernameStr = request.getParameter("username");
        String dobStr = request.getParameter("dob");
        String phoneNumStr = request.getParameter("phoneNum");
        String emailStr = request.getParameter("email");

        User currentUser = userService.getUserByName(usernameStr);
        System.out.println("User : " + currentUser);

        try {
            User userNew = currentUser;
            userNew.setUsername(usernameStr);

            if (dobStr != null && !dobStr.isEmpty()) {
                // Directly convert the date string to java.sql.Date
                java.sql.Date sqlDate = java.sql.Date.valueOf(dobStr);
                userNew.setDob(sqlDate);
            } else {
                userNew.setDob(null); // Handle the case when dobStr is empty
            }

            userNew.setPhoneNum(phoneNumStr);
            userNew.setEmail(emailStr);

            profileService.updateUserBio(userNew);

            // Set success message
            request.setAttribute("successMessage", "User bio updated successfully!");
            retrieveCategoryData(request);
            request.getRequestDispatcher("updateProfile.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while updating the user bio!");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }

    private void retrieveCategoryData(HttpServletRequest request) {
        ArrayList<Category> categoryList = BannerService.getCategory();
        request.setAttribute("categoryList", categoryList);
    }
}
