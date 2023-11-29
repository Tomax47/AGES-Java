package org.AGES.servlet.user;

import org.AGES.model.FileInfo;
import org.AGES.repository.file.FileRWService;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/profile_image")
public class UserProfileImageServLet extends HttpServlet {
    private FileRWService fileRWService;
    private static final String NO_USER_PROFILE_IMAGE_FOUND_ERROR = "User has no profile image!";

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
       fileRWService = (FileRWService) servletContext.getAttribute("fileRWService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");
        FileInfo fileInfo = null;

        // Fetch the user's image file info from the file service
        try {
            fileInfo = fileRWService.getUserFileInfo(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Passing the image-file in
        if (fileInfo != null) {
            resp.setContentType(fileInfo.getType());
            resp.setContentLength((int) fileInfo.getSize());
            resp.setHeader("Content-Description", "filename=\"" + fileInfo.getOriginalFileName() + "\"");

            try {
                fileRWService.writeUserFileFromStorage(userId, resp.getOutputStream());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            resp.flushBuffer();
        } else {
            req.getSession().setAttribute("NoUserProfileImageError", NO_USER_PROFILE_IMAGE_FOUND_ERROR);
            req.getRequestDispatcher("/jsp/userprofile.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

}
