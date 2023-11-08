package org.AGES.repository;

import org.AGES.model.User;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public interface UserCRUDRepo extends CRUDRepo<User> {
    boolean login(String email, String password) throws SQLException;

    User findByEmail(String email);
    User findById(Long userId);
    void saveUserLoginCookie(String cookieUUID, long userId) throws SQLException;
    void updateUserInformation(String name, String surname, int age, String number, String address, String email) throws SQLException;
    long getUserId(String email);
    long checkUserExistenceByLoginCookie(String loginCookie) throws SQLException;
}
