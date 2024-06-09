package Controller;

import Service.UserService;
import Entity.User;
import Service.ProfileService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
@WebServlet(name = "AddImageServlet", value = "/AddImageServlet")
public class AddImageServlet extends HttpServlet {
    private UserService userService;
    private ProfileService profileService;

    public void init() {
        userService = new UserService();
        profileService = new ProfileService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set the request encoding
        request.setCharacterEncoding("UTF-8");

        // Set the response content type and encoding
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String usernameStr = request.getParameter("username");
        User user = userService.getUserByName(usernameStr);

        System.out.println("In do post method of Add Image servlet.");
        Part file=request.getPart("image");

        // Get the submitted file name and its extension
        String submittedFileName = file.getSubmittedFileName();
        String fileExtension = submittedFileName.substring(submittedFileName.lastIndexOf("."));

        // Construct the image file name based on the user, timestamp, and file extension
        String imageFileName = user.getUsername() + "-" + System.currentTimeMillis() + fileExtension;
        System.out.println("Generated Image File Name: " + imageFileName);

        String uploadPath = "D:/3rd year sem 2/Group_Project_Ruanjian_Maven/ruanjiansheji2024/src/main/webapp/Images/Uploads/" + imageFileName;
        System.out.println("Upload Path : " + uploadPath);

        // Uploading our selected image into the images folder
        try {
            FileOutputStream fos = new FileOutputStream(uploadPath);
            InputStream is = file.getInputStream();

            byte[] data = new byte[is.available()];
            is.read(data);
            fos.write(data);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Update the database with the image path
        try {
            String imagePath = "Images/Uploads/" + imageFileName;
            profileService.updateImagePath(imagePath, user.getId());
            response.getWriter().write(imagePath);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
