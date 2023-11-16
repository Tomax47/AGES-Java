package org.AGES.repository.user;

import org.AGES.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserCRUDRepoImpl implements UserCRUDRepo{

    private DataSource dataSource;
    private PasswordEncoder passwordEncoder;
    private static final String INSERT_INTO_USERS = "INSERT INTO users(name,surname,email,password) VALUES ";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email=";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id=";
    private static final String INSERT_INTO_USERS_COOKIES = "INSERT INTO users_cookies(login_cookie_uuid,user_id) VALUES ";
    private static final String SELECT_USER_BY_LOGIN_COOKIE_UUID = "SELECT user_id FROM users_cookies WHERE login_cookie_uuid=";
    private static final String UPDATE_USER_INFO = "UPDATE users SET name=?, surname=?, age=?, number=?, address=?, image=? WHERE id=?";
    private static final String DELETE_FROM_USERS = "DELETE FROM users WHERE email=";
    private static final String DELETE_FROM_USERS_COOKIES = "DELETE FROM users_cookies WHERE user_id=";
    private static final String DELETE_USERS_PRODUCTS_FROM_PRODUCTS = "DELETE FROM products WHERE seller_id=";
    private static final String INSERT_INTO_USERS_BLACKLIST = "INSERT INTO users_blacklist(email) VALUES ";
    private static final String SELECT_ALL_FROM_USERS_BLACKLIST = "SELECT * FROM users_blacklist WHERE email=";

    public UserCRUDRepoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public int save(User user) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {

            int success = 0;
            //Checking if the email ain't blacklisted
            PreparedStatement isBlackListedCheck = connection.prepareStatement(SELECT_ALL_FROM_USERS_BLACKLIST+"(?)");
            isBlackListedCheck.setString(1,user.getEmail());
            ResultSet blackListedResultSet = isBlackListedCheck.executeQuery();


            if (!blackListedResultSet.next()) {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_USERS+"(?,?,?,?)");
                System.out.println("USER IS GETTING INSERTED : "+user.getName()+" "+user.getSurname());
                preparedStatement.setString(1,user.getName());
                preparedStatement.setString(2, user.getSurname());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getPassword());


                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                //Checking the process if done
                System.out.println("RESULT-SET GEN-KEYS : "+resultSet);
                //Registered
                success = 1;
            } else {
                //Blacklisted email
                success = 2;
            }

            System.out.println("USER REGISTERED STATE -> "+success);
            return success;

        } catch (SQLException e) {
            //TODO: HANDLE THE EXCEPTION IN A BETTER WAY
            throw new SQLException(e);
        }
    }

    public boolean login(String email, String password) throws SQLException {
        System.out.println("CHECKING THE PASSWORD");
        User user = findByEmail(email);

        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                System.out.println("PASSWD MATCHED");
                return true;
            } else {
                System.out.println("PASSWD AINT MATCHED");
                return false;
            }
        } else {
            System.out.println("USER IS NULL");
            return false;
        }
    }

    @Override
    public User findByEmail(String email) {
        User user = null;
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL+"(?)");
            preparedStatement.setString(1,email);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = User.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .surname(resultSet.getString("surname"))
                        .age(resultSet.getInt("age"))
                        .number(resultSet.getString("number"))
                        .address(resultSet.getString("address"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .role(resultSet.getString("role"))
                        .build();
            }
            return user;

        } catch (SQLException e) {
            throw new RuntimeException("Error registering user: " + e.getMessage(), e);
        }
    }

    @Override
    public User findById(Long userId) {
        User user = null;
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID+"(?)");
            preparedStatement.setLong(1,userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = User.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .surname(resultSet.getString("surname"))
                        .age(resultSet.getInt("age"))
                        .number(resultSet.getString("number"))
                        .address(resultSet.getString("address"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .image(resultSet.getBytes("image"))
                        .role(resultSet.getString("role"))
                        .build();
            }
            return user;

        } catch (SQLException e) {
            throw new RuntimeException("Error registering user: " + e.getMessage(), e);
        }
    }

    @Override
    public void saveUserLoginCookie(String cookieUUID, long userId) throws SQLException {
        Connection connection = dataSource.getConnection();

        System.out.println("A LOGIN COOKIE IS BEING INSERTED : "+cookieUUID+" ,UserId : "+userId);
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_USERS_COOKIES+"(?,?)");
        preparedStatement.setString(1,cookieUUID);
        preparedStatement.setLong(2,userId);

        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        //Checking the process if done
        System.out.println("COOKIE HAS BEEN INSERTED -> RESULT-SET GEN-KEYS : "+resultSet);
    }

    @Override
    public void updateUserInformation(String name, String surname, int age, String number, String address, String email, byte[] image, long userId) throws SQLException {
        User user = findByEmail(email);
        System.out.println("User has been found |UPDATING| -> "+email);
        //Checking if user's req is valid & authorized
        if (user != null && user.getId() == userId) {
            try {
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_INFO);

                preparedStatement.setString(1,name);
                preparedStatement.setString(2,surname);
                preparedStatement.setInt(3,age);
                preparedStatement.setString(4,number);
                preparedStatement.setString(5,address);
                preparedStatement.setBytes(6,image);
                preparedStatement.setLong(7,user.getId());

                int affectedRows = preparedStatement.executeUpdate();

                //Process Check
                if (affectedRows > 0) {
                    System.out.println("User has been updated!");
                } else {
                    System.out.println("Cant update the user!");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("UNAUTHORIZED ACTION -> CAN'T CHANGE DATA OF THE EMAIL THAT AIN'T YOURS!");
        }
    }

    @Override
    public long getUserId(String email) {
        long userId = 0;
        try {
            User user = findByEmail(email);
            if (user != null) {
                userId = user.getId();
            }
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }
        return userId;
    }

    @Override
    public long checkUserExistenceByLoginCookie(String loginCookie) throws SQLException{
        long userId = 0;
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN_COOKIE_UUID + "(?)");
            preparedStatement.setString(1, loginCookie);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userId = resultSet.getLong("user_id");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userId;
    }

    public boolean userIsAdmin(long userId) {
        User user = findById(userId);
        if (user != null) {
            return user.getRole().equals("Admin");
        } else {
            return false;
        }
    }

    @Override
    public int deleteUser(String userEmail) throws SQLException {
        User user = findByEmail(userEmail);

        if (user != null && !user.getRole().equals("Admin")) {
            //Deleting the user
            try (Connection connection = dataSource.getConnection()) {
                //Deleting the cookies associated with the user
                PreparedStatement deleteUserCookiesPreparedStatement = connection.prepareStatement(DELETE_FROM_USERS_COOKIES+"?");
                deleteUserCookiesPreparedStatement.setLong(1,user.getId());
                deleteUserCookiesPreparedStatement.executeUpdate();
                System.out.println("USER COOKIES HAS BEEN DELETED!");

                //Deleting user's published products
                PreparedStatement deleteUserProductsPreparedStatement = connection.prepareStatement(DELETE_USERS_PRODUCTS_FROM_PRODUCTS+"?");
                deleteUserProductsPreparedStatement.setLong(1,user.getId());
                deleteUserProductsPreparedStatement.executeUpdate();
                System.out.println("USER PRODUCTS HAS BEEN DELETED!");

                //Deleting the user
                PreparedStatement deleteUserPreparedStatement = connection.prepareStatement(DELETE_FROM_USERS+"(?)");
                deleteUserPreparedStatement.setString(1,userEmail);
                deleteUserPreparedStatement.executeUpdate();
                System.out.println("USER HAS BEEN DELETED -> "+userEmail);

                //BlackListing user's email
                PreparedStatement blackListUserEmail = connection.prepareStatement(INSERT_INTO_USERS_BLACKLIST+"(?)");
                blackListUserEmail.setString(1,userEmail);
                blackListUserEmail.executeUpdate();
                System.out.println("USER HAS BEEN BLACK LISTED!");

                return 1;
            } catch (SQLException e) {
                throw new SQLException(e);
            }
        } else if (user == null ){
            //User ain't exist
            System.out.println("NULL VALUE! CAN'T DELETE A NULL USER -> "+userEmail);
            return 0;
        } else {
            System.out.println("Admin! CAN'T DELETE AN ADMIN -> "+userEmail);
            return 2;
        }
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = User.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .surname(resultSet.getString("surname"))
                        .age(resultSet.getInt("age"))
                        .number(resultSet.getString("number"))
                        .address(resultSet.getString("address"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .role(resultSet.getString("role"))
                        .build();
                users.add(user);
            }
            return users;

        } catch (SQLException e) {
            throw new RuntimeException("Error registering user: " + e.getMessage(), e);
        }
    }
}
