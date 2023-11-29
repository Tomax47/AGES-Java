package org.AGES.servlet.user;

import org.AGES.dto.UserDto;

import org.AGES.repository.file.FileRWService;
import org.AGES.repository.user.UserCRUDRepo;
import org.AGES.repository.user.UserGetInformationService;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

@WebServlet("/delete_account")
public class UserDeleteAccountServLet extends HttpServlet {

    private UserGetInformationService userGetInformationService;
    private UserCRUDRepo userCRUDRepo;
    private FileRWService fileRWService;

    private static final String ErrorDeleteProfileMsg = "Something went wrong! profile deletion couldn't be done";

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        userGetInformationService = (UserGetInformationService) servletContext.getAttribute("UserGetInformationService");
        userCRUDRepo = (UserCRUDRepo) servletContext.getAttribute("UserCRUDRepo");
        fileRWService = (FileRWService) servletContext.getAttribute("fileRWService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/delete_account.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("userId");

        try {
            //Getting user's info to pass the email for the delete method
            UserDto userDto = userGetInformationService.getUserInformation(userId);
            int deleteUser = userCRUDRepo.deleteUser(userDto.getEmail());

            if (deleteUser == 1) {
                //User has been deleted, now to delete the images the user has uploaded
                fileRWService.deleteUserProfileImage(userId);

                //Closing the opened session
                req.getSession().invalidate();
                //TODO: FIND A WAY TO INVALIDATE THE LOGIN COOKIE
                resp.sendRedirect("/");
            } else {
                req.getSession().setAttribute("ErrorDeletingProfile", ErrorDeleteProfileMsg);
            }
        } catch (SQLException e) {
            resp.sendRedirect("/profile");
        }

    }

}
