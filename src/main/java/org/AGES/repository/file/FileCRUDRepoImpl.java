package org.AGES.repository.file;

import org.AGES.model.FileInfo;

import javax.sql.DataSource;
import java.io.CharArrayReader;
import java.io.File;
import java.sql.*;
import java.util.List;

public class FileCRUDRepoImpl implements FileCRUDRepo {
    private DataSource dataSource;
    private static final String INSERT_INTO_USERS_PROFILE_IMAGES = "INSERT INTO users_profile_images(original_file_name, storage_file_name, size, type, user_id) VALUES ";
    private static final String UPDATE_USER_PROFILE_IMAGE = "UPDATE users_profile_images SET original_file_name=?, storage_file_name=?, size=?, type=? WHERE user_id=?";
    private static final String INSERT_INTO_PRODUCTS_IMAGES = "INSERT INTO products_images(original_file_name, storage_file_name, size, type, product_id) VALUES ";
    private static final String SELECT_FROM_USERS_PROFILE_IMAGES_BY_USERID = "SELECT * FROM users_profile_images WHERE user_id=";
    private static final String SELECT_FROM_PRODUCTS_IMAGES_BY_PRODUCT_ID = "SELECT * FROM products_images WHERE product_id=";
    private static final String DELETE_FROM_PRODUCTS_IMAGES_BY_PRODUCT_ID = "DELETE FROM products_images WHERE product_id=";
    private static final String DELETE_FROM_USERS_PROFILE_IMAGES_BY_USER_ID = "DELETE FROM users_profile_images WHERE user_id=";
    public FileCRUDRepoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public int save(FileInfo fileInfo) throws SQLException {
        FileInfo fileDB = null;

        Connection connection = dataSource.getConnection();

        if (fileInfo.getUserId() != null && fileInfo.getProductId() == null ){
            // Checking if the user already has an image
            fileDB = findByUserId(fileInfo.getUserId());
            if (fileDB == null) {
                // Inserting user profile image
                addUserImage(connection, fileInfo);
                return 1;

            } else {
                // Updating user's image
                updateUserImage(connection, fileInfo);
                return 1;
            }

        } else if (fileInfo.getProductId() != null && fileInfo.getUserId() == null) {
            // Inserting product image
            addProductImage(connection, fileInfo);
            return 1;

        } else {
            //Error
            return 0;
        }
    }

    @Override
    public FileInfo findByUserId(long userId) throws SQLException {
        FileInfo fileInfo = null;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_USERS_PROFILE_IMAGES_BY_USERID+"(?)");
            preparedStatement.setLong(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                fileInfo = FileInfo.builder()
                        .originalFileName(resultSet.getString("original_file_name"))
                        .storageFileName(resultSet.getString("storage_file_name"))
                        .size(resultSet.getLong("size"))
                        .type(resultSet.getString("type"))
                        .userId(resultSet.getLong("user_id"))
                        .build();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return fileInfo;
    }

    @Override
    public FileInfo findByProductId(long productId) throws SQLException {
        FileInfo fileInfo = null;

        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_PRODUCTS_IMAGES_BY_PRODUCT_ID+"(?)");
            preparedStatement.setLong(1,productId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                fileInfo = FileInfo.builder()
                        .originalFileName(resultSet.getString("original_file_name"))
                        .storageFileName(resultSet.getString("storage_file_name"))
                        .size(resultSet.getLong("size"))
                        .type(resultSet.getString("type"))
                        .productId(resultSet.getLong("product_id"))
                        .build();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return fileInfo;
    }

    private void addUserImage(Connection connection, FileInfo fileInfo) throws SQLException {
        try {
            System.out.println("INSERTING A USER IMAGE!");
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_USERS_PROFILE_IMAGES+"(?,?,?,?,?)");
            preparedStatement.setString(1, fileInfo.getOriginalFileName());
            preparedStatement.setString(2, fileInfo.getStorageFileName());
            preparedStatement.setLong(3, fileInfo.getSize());
            preparedStatement.setString(4, fileInfo.getType());
            preparedStatement.setLong(5, fileInfo.getUserId());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            System.out.println("USER IMAGE HAS BEEN INSERTED -> "+resultSet);

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private void updateUserImage(Connection connection, FileInfo fileInfo) throws SQLException {
        try {
            System.out.println("UPDATING A USER IMAGE!");
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_PROFILE_IMAGE);
            preparedStatement.setString(1, fileInfo.getOriginalFileName());
            preparedStatement.setString(2, fileInfo.getStorageFileName());
            preparedStatement.setLong(3, fileInfo.getSize());
            preparedStatement.setString(4, fileInfo.getType());
            preparedStatement.setLong(5, fileInfo.getUserId());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            System.out.println("USER IMAGE HAS BEEN INSERTED -> "+resultSet);

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
    private void addProductImage(Connection connection, FileInfo fileInfo) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_PRODUCTS_IMAGES+"(?,?,?,?,?)");
            preparedStatement.setString(1, fileInfo.getOriginalFileName());
            preparedStatement.setString(2, fileInfo.getStorageFileName());
            preparedStatement.setLong(3, fileInfo.getSize());
            preparedStatement.setString(4, fileInfo.getType());
            preparedStatement.setLong(5, fileInfo.getProductId());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            System.out.println("PRODUCT IMAGE HAS BEEN INSERTED -> "+resultSet);

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int deleteProductImage(long productId) throws SQLException {
        FileInfo fileInfo = findByProductId(productId);
        if (fileInfo != null) {
            try (Connection connection = dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FROM_PRODUCTS_IMAGES_BY_PRODUCT_ID+"(?)");
                preparedStatement.setLong(1, productId);

                preparedStatement.executeUpdate();

                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                System.out.println("DELETED PRODUCT'S IMAGE SUCCESSFULLY -> "+resultSet);
                return 1;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            return 0;
        }
    }

    @Override
    public int deleteUserProfileImage(long userId) throws SQLException {
        FileInfo userProfileImage = findByUserId(userId);

        if (userProfileImage != null) {

            try (Connection connection = dataSource.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FROM_USERS_PROFILE_IMAGES_BY_USER_ID+"(?)");
                preparedStatement.setLong(1, userId);

                preparedStatement.executeUpdate();
                ResultSet resultSet= preparedStatement.getGeneratedKeys();

                System.out.println("USER PROFILE IMAGE HAS BEEN DELETED!");
                return 1;
            } catch (SQLException e) {
                throw new SQLException(e);
            }
        } else {
            return 0;
        }
    }
}
