package org.AGES.repository.product;

import org.AGES.model.Product;
import org.AGES.repository.CRUDRepo;

import java.sql.SQLException;

public interface ProductCRUDRepo extends CRUDRepo<Product> {

    int save(Product product) throws SQLException;
    void deleteProduct(long product_id, long userId);
    Product findById(long productId);
}
