package org.AGES.servlet.product;

import org.AGES.model.FileInfo;
import org.AGES.repository.file.FileRWService;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/product_image")
public class ProductImageServLet extends HttpServlet {
    private FileRWService fileRWService;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        fileRWService = (FileRWService) servletContext.getAttribute("fileRWService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long productId = Long.parseLong(req.getParameter("product_id"));
        FileInfo fileInfo = null;

        // Fetch the user's image file info from the file service
        try {
            fileInfo = fileRWService.getProductFileInfo(productId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Passing the image-file in
        resp.setContentType(fileInfo.getType());
        resp.setContentLength((int) fileInfo.getSize());
        resp.setHeader("Content-Description", "filename=\"" + fileInfo.getOriginalFileName() + "\"");

        try {
            fileRWService.writeProductFileFromStorage(productId, resp.getOutputStream());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.flushBuffer();
    }

}
