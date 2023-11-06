package org.AGES.repository;

import com.sun.security.auth.NTUserPrincipal;
import org.AGES.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.*;
import java.util.List;

public class UserCRUDRepoImpl implements UserCRUDRepo{

    private DataSource dataSource;
    private PasswordEncoder passwordEncoder;
    private static final String INSERT_INTO_USERS = "INSERT INTO users(name,surname,email,password) VALUES ";
    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email=";

    public UserCRUDRepoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public void save(User user) throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println("USER IS GETTING INSERTED : "+user.getName());
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_USERS+"(?,?,?,?)");
        preparedStatement.setString(1,user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());

        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        //Checking the process if done
        System.out.println("RESULT-SET GEN-KEYS : "+resultSet);
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

    public List findAll() {
        return null;
    }
}
