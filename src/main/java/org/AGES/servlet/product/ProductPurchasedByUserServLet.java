package org.AGES.servlet.product;

import org.AGES.model.Product;
import org.AGES.repository.product.ProductCRUDRepo;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/purchased_products")
public class ProductPurchasedByUserServLet extends HttpServlet {

    private ProductCRUDRepo productCRUDRepo;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        productCRUDRepo = (ProductCRUDRepo) servletContext.getAttribute("ProductCRUDRepo");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");
        System.out.println("USER'S PURCHASED PRODUCTS -> USER-ID : "+userId);
        List<Product> products = null;

        try {
            products = productCRUDRepo.findUserPurchasedProducts(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Is products' list null? -> "+products.isEmpty());
        req.setAttribute("purchasedProducts", products);
        req.getRequestDispatcher("jsp/user_bought_products.jsp").forward(req, resp);



    }

}
