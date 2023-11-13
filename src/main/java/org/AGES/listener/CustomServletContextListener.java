package org.AGES.listener;

import org.AGES.repository.product.ProductCRUDRepo;
import org.AGES.repository.product.ProductCRUDRepoImpl;
import org.AGES.repository.product.ProductRegistrationService;
import org.AGES.repository.product.ProductRegistrationServiceImpl;
import org.AGES.repository.user.UserCRUDRepo;
import org.AGES.repository.user.UserCRUDRepoImpl;
import org.AGES.repository.user.UserRegistrationService;
import org.AGES.repository.user.UserRegistrationServiceImpl;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
@WebListener("/register")
public class CustomServletContextListener implements ServletContextListener {
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ancient_goods_estore";

    private static final String DB_DRIVER = "org.postgresql.Driver";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUsername(DB_USER);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setUrl(DB_URL);

        ServletContext servletContext = servletContextEvent.getServletContext();

        //User
        UserCRUDRepo userCRUDRepo = new UserCRUDRepoImpl(dataSource);
        servletContext.setAttribute("UserCRUDRepo",userCRUDRepo);
        UserRegistrationService userRegistrationService= new UserRegistrationServiceImpl(userCRUDRepo);
        servletContext.setAttribute("UserRegistrationService",userRegistrationService);

        //Product
        ProductCRUDRepo productCRUDRepo = new ProductCRUDRepoImpl(dataSource);
        servletContext.setAttribute("ProductCRUDRepo", productCRUDRepo);
        ProductRegistrationService productRegistrationService = new ProductRegistrationServiceImpl(productCRUDRepo);
        servletContext.setAttribute("ProductRegistrationService", productRegistrationService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
