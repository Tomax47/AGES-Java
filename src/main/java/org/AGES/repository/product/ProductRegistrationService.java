package org.AGES.repository.product;

import org.AGES.dto.ProductAddFrom;

import java.sql.SQLException;

public interface ProductRegistrationService {
    void addProduct(ProductAddFrom productAddFrom) throws SQLException;
}
