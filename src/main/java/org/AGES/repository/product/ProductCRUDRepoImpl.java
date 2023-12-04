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
    private static final String INSERT_INTO_PRODUCTS = "INSERT INTO products(product_name, product_description, price, seller_id) VALUES";
    private static final String SELECT_PRODUCT_BY_NAME_AND_DESCRIPTION = "SELECT id FROM products WHERE product_name=? AND product_description=?";
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM products";
    private static final String SELECT_USER_PRODUCTS = "SELECT * FROM products WHERE seller_id=";
    private static final String DELETE_PRODUCT_FROM_PRODUCTS = "DELETE FROM products WHERE id=";
    private static final String SELECT_PRODUCT_FROM_PRODUCTS = "SELECT * FROM products WHERE id=";
    private static final String INSERT_INTO_SOLD_PRODUCTS = "INSERT INTO sold_products(id, product_name, product_description, price, seller_id, buyer_id) VALUES";
    private static final String SELECT_FROM_SOLD_PRODUCTS_BY_USER_ID = "SELECT * FROM sold_products WHERE buyer_id=";

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
                        .id(resultSet.getLong("id"))
                        .productName(resultSet.getString("product_name"))
                        .productDescription(resultSet.getString("product_description"))
                        .price(resultSet.getDouble("price"))
                        .sellerId(resultSet.getLong("seller_id"))
                        .build();

                products.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public Long saveProduct(Product product) throws SQLException {
        System.out.println("PRODUCT IS BEING SAVED AFTER THE REGISTRATION FORM CALLED THE METHOD!");
        System.out.println("PRODUCT IS GETTING INSERTED : "+product.getProductName());

        Long productId = null;
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_PRODUCTS+"(?,?,?,?)");
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setString(2, product.getProductDescription());
            preparedStatement.setDouble(3,product.getPrice());
            preparedStatement.setLong(4, product.getSellerId());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            productId = getProductId(connection, product.getProductName(), product.getProductDescription());

            // Null value for the product_id means that the insertion of the product ain't go correctly!
            return productId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long getProductId(Connection connection, String productName, String productDescription) throws SQLException {
        Long productId = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_NAME_AND_DESCRIPTION);
            preparedStatement.setString(1,productName);
            preparedStatement.setString(2,productDescription);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                productId = resultSet.getLong("id");
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return productId;
    }

    @Override
    public int deleteProduct(long productId, long userId) {
        Product product = findById(productId);

        if (product != null && product.getSellerId() == userId) {
            try (Connection connection = dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_FROM_PRODUCTS+"(?)");
                preparedStatement.setLong(1, productId);

                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                System.out.println("PRODUCT HAS BEEN DELETED -> "+resultSet);
                return 1;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else  {
            return 0;
        }
    }

    @Override
    public int deleteProductWhenBuy(long productId) {
        //Deleting the product from the products table after it's been bought!
        Product product = findById(productId);

        if (product != null) {
            try (Connection connection = dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_FROM_PRODUCTS+"(?)");
                preparedStatement.setLong(1, productId);

                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                System.out.println("PRODUCT HAS BEEN DELETED AFTER BEING BOUGHT -> "+resultSet);
                return 1;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else  {
            return 0;
        }
    }

    @Override
    public List<Product> findUserProducts(long userId) throws SQLException {
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_PRODUCTS+"(?)");
            preparedStatement.setLong(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = Product.builder()
                        .id(resultSet.getLong("id"))
                        .productName(resultSet.getString("product_name"))
                        .productDescription(resultSet.getString("product_description"))
                        .price(resultSet.getDouble("price"))
                        .sellerId(resultSet.getLong("seller_id"))
                        .build();

                products.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public List<Product> findUserPurchasedProducts(long userId) throws SQLException {
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_SOLD_PRODUCTS_BY_USER_ID+"(?)");
            preparedStatement.setLong(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = Product.builder()
                        .id(resultSet.getLong("id"))
                        .productName(resultSet.getString("product_name"))
                        .productDescription(resultSet.getString("product_description"))
                        .price(resultSet.getDouble("price"))
                        .sellerId(resultSet.getLong("seller_id"))
                        .build();

                products.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public int saveToSoldProducts(Product boughtProduct, long buyerId) throws SQLException {
        //Checking for the existence of the product, and the non-emptiness of the userId
        System.out.println("SAVING THE PRODUCT TO THE SOLD PRODUCTS TABLE!");
        if (boughtProduct != null && !String.valueOf(buyerId).isBlank()) {
            try (Connection connection = dataSource.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_SOLD_PRODUCTS+"(?,?,?,?,?,?)");
                preparedStatement.setLong(1, boughtProduct.getId());
                preparedStatement.setString(2, boughtProduct.getProductName());
                preparedStatement.setString(3, boughtProduct.getProductDescription());
                preparedStatement.setDouble(4,boughtProduct.getPrice());
                preparedStatement.setLong(5, boughtProduct.getSellerId());
                preparedStatement.setLong(6, buyerId);

                preparedStatement.executeUpdate();

                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                return 1;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            return 0;
        }
    }

    @Override
    public Product findById(long productId) {
        Product product = null;

        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_FROM_PRODUCTS+"(?)");
            preparedStatement.setLong(1, productId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product = Product.builder()
                        .id(resultSet.getLong("id"))
                        .productName(resultSet.getString("product_name"))
                        .productDescription(resultSet.getString("product_description"))
                        .price(resultSet.getDouble("price"))
                        .sellerId(resultSet.getLong("seller_id"))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return product;
    }

    //NOT NEEDED FOR THE CLASS IN THE RIGHT MOMENT "USED IN THE PUBLIC CRUD INTERFACE"
    @Override
    public int save(Product object) throws SQLException {
        return 0;
    }
}
