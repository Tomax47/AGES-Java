package org.AGES.servlet;

import org.AGES.dto.UserDto;
import org.AGES.repository.user.UserCRUDRepo;
import org.AGES.repository.user.UserGetInformationService;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/users_index")
public class UsersDisplayServLet extends HttpServlet {
    private UserCRUDRepo userCRUDRepo;
    private UserGetInformationService userGetInformationService;
    private static final String adminUserBanMsg = "User has been baned!";
    private static final String adminUserBanError = "Something went wrong, user ain't exist!";
    private static final String adminAdminBanError = "You can't ban an Admin!";

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        userCRUDRepo = (UserCRUDRepo) servletContext.getAttribute("UserCRUDRepo");
        userGetInformationService = (UserGetInformationService) servletContext.getAttribute("UserGetInformationService");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDto> users =  userGetInformationService.findAll();

        System.out.println("IS THE USERS LIST EMPTY? -> "+users.isEmpty());
        req.setAttribute("usersList",users);
        req.getRequestDispatcher("jsp/users.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userEmail = req.getParameter("user_email");

        try {
            int deleteUser = userCRUDRepo.deleteUser(userEmail);

            if (deleteUser == 1){
                //User has been baned
                req.getSession().setAttribute("AdminUserBanMsg",adminUserBanMsg);
                resp.sendRedirect("users_index");
            } else if (deleteUser == 2) {
                //Admin banning admin error
                req.getSession().setAttribute("AdminUserBanMsg",adminAdminBanError);
                resp.sendRedirect("users_index");
            } else {
                //User ain't exist
                req.getSession().setAttribute("AdminUserBanMsg",adminUserBanError);
                resp.sendRedirect("users_index");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
