package org.AGES.servlet;

import org.AGES.model.User;
import org.AGES.repository.UserCRUDRepo;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

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

        try {
            userCRUDRepo.updateUserInformation(name, surname, age, number, address, email);
            resp.sendRedirect("/profile");
        } catch (SQLException e) {
            //TODO: HANDLE IN A BETTER WAY
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        }
    }
}
