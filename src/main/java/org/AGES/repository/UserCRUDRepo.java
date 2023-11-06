package org.AGES.repository;

import org.AGES.model.User;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public interface UserCRUDRepo extends CRUDRepo<User> {
    boolean login(String email, String password) throws SQLException;

    User findByEmail(String email);
    void saveUserLoginCookie(String cookieUUID, long userId) throws SQLException;
    long getUserId(String email);
}
