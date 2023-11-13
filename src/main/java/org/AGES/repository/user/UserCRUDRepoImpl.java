package org.AGES.repository.user;

import org.AGES.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserCRUDRepoImpl implements UserCRUDRepo{

    private DataSource dataSource;
    private PasswordEncoder passwordEncoder;
    private static final String INSERT_INTO_USERS = "INSERT INTO users(name,surname,email,password) VALUES ";
    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email=";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id=";
    private static final String INSERT_INTO_USERS_COOKIES = "INSERT INTO users_cookies(login_cookie_uuid,user_id) VALUES ";
    private static final String SELECT_USER_BY_LOGIN_COOKIE_UUID = "SELECT user_id FROM users_cookies WHERE login_cookie_uuid=";
    private static final String UPDATE_USER_INFO = "UPDATE users SET name=?, surname=?, age=?, number=?, address=?, image=? WHERE id=?";

    public UserCRUDRepoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public void save(User user) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_USERS+"(?,?,?,?)");
            System.out.println("USER IS GETTING INSERTED : "+user.getName());
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            //Checking the process if done
            System.out.println("RESULT-SET GEN-KEYS : "+resultSet);

        } catch (SQLException e) {
            //TODO: HANDLE THE EXCEPTION
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

    public List findAll() {
        return null;
    }
}
