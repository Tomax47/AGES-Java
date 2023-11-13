package org.AGES.servlet;

import org.AGES.model.User;
import org.AGES.repository.user.UserCRUDRepo;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 2,
        maxRequestSize = 1024 * 1024 * 3
)
@WebServlet("/profile")
public class UserProfileServLet extends HttpServlet {
    private UserCRUDRepo userCRUDRepo;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        userCRUDRepo = (UserCRUDRepo) servletContext.getAttribute("UserCRUDRepo");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO: CHECK OUT WHY THE IMAGE IS DISPLAYING DECODED! + EDIT THE METHOD SO IT SEND THE USER'S NECESSARY INFO ONLY AND NOT ALL OF THE USER'S INTO AS A USER OBJECT!
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");
        User user = userCRUDRepo.findById(userId);

        //Passing the user in
        req.setAttribute("user", user);
        req.getRequestDispatcher("jsp/userprofile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        int age = Integer.parseInt(req.getParameter("age"));
        String number = req.getParameter("number");
        String address = req.getParameter("address");
        String email = req.getParameter("email");
        HttpSession session = req.getSession();
        long userId = (Long) session.getAttribute("userId");

        //Fetching the image
        Part filePart = req.getPart("image");

        byte[] image = null;

        if (filePart != null) {
            InputStream inputStream = filePart.getInputStream();
            image = new byte[(int) filePart.getSize()];
            inputStream.read(image);
        }

        System.out.println("Image :"+image);

        try {
            userCRUDRepo.updateUserInformation(name, surname, age, number, address, email, image, userId);
            resp.sendRedirect("/profile");
        } catch (SQLException e) {
            //TODO: HANDLE IN A BETTER WAY
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        }
    }
}
