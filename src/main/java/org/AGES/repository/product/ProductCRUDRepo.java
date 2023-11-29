package org.AGES.repository.product;

import org.AGES.model.Product;
import org.AGES.repository.CRUDRepo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ProductCRUDRepo extends CRUDRepo<Product> {
    int save(Product object) throws SQLException;
    Long saveProduct(Product product) throws SQLException;
    Long getProductId(Connection connection, String productName, String productDescription) throws SQLException;
    int deleteProduct(long productId, long userId);
    Product findById(long productId);
    List<Product> findUserProducts(long userId) throws SQLException;

}
