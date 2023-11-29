package org.AGES.servlet.user;

import org.AGES.dto.UserDto;
import org.AGES.model.FileInfo;
import org.AGES.repository.file.FileRWService;
import org.AGES.repository.user.UserCRUDRepo;
import org.AGES.repository.user.UserGetInformationService;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 2,
        maxRequestSize = 1024 * 1024 * 3
)
@WebServlet("/profile")
public class UserProfileServLet extends HttpServlet {
    private UserCRUDRepo userCRUDRepo;
    private UserGetInformationService userGetInformationService;
    private FileRWService fileRWService;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        userCRUDRepo = (UserCRUDRepo) servletContext.getAttribute("UserCRUDRepo");
        userGetInformationService = (UserGetInformationService) servletContext.getAttribute("UserGetInformationService");
        fileRWService = (FileRWService) servletContext.getAttribute("fileRWService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");
        UserDto user = null;

        try {
            user = userGetInformationService.getUserInformation(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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

        // Image fetching
        Part part = req.getPart("image");

        //User & Image insertion
        try {
            userCRUDRepo.updateUserInformation(name, surname, age, number, address, email, userId);
            if (part.getSize() != 0) {
                fileRWService.saveFileToStorage(part.getInputStream(), part.getSubmittedFileName(), part.getContentType(), part.getSize(), userId, null);
            }
            resp.sendRedirect("/profile");
        } catch (SQLException e) {
            //TODO: HANDLE IN A BETTER WAY
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        }
    }
}
