package org.AGES.repository.product;

import org.AGES.model.Product;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCRUDRepoImpl implements ProductCRUDRepo{
    private DataSource dataSource;
    private static final String INSERT_INTO_PRODUCTS = "INSERT INTO products(product_name, product_description, price, seller_id, product_image) VALUES";
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM products";

    public ProductCRUDRepoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {

        List<Product> products = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = Product.builder()
                        .productName(resultSet.getString("product_name"))
                        .productDescription(resultSet.getString("product_description"))
                        .price(resultSet.getDouble("price"))
                        .sellerId(resultSet.getLong("seller_id"))
                        .productImage(null) //TODO: ADD THE IMAGE HERE
                        .build();

                products.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public int save(Product product) throws SQLException {
        System.out.println("PRODUCT IS BEING SAVE AFTER THE REGISTRATION FORM CALLED THE METHOD!");
        System.out.println("PRODUCT IS GETTING INSERTED : "+product.getProductName());

        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_PRODUCTS+"(?,?,?,?,?)");
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setString(2, product.getProductDescription());
            preparedStatement.setDouble(3,product.getPrice());
            preparedStatement.setLong(4, product.getSellerId());
            preparedStatement.setBytes(5,product.getProductImage());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            //Checking the process if done
            System.out.println("RESULT-SET GEN-KEYS : "+resultSet+" -> PRODUCT HAS BEEN SAVED!");

            //TODO: TAKE INTO ACCOUNT THE 0 RETURNED VALUE
            return 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteProduct(long product_id, long userId) {
    }

    @Override
    public Product findById(long productId) {
        return null;
    }
}
