package org.AGES.repository.user;

import org.AGES.model.User;
import org.AGES.repository.CRUDRepo;
import java.sql.SQLException;

public interface UserCRUDRepo extends CRUDRepo<User> {
    boolean login(String email, String password) throws SQLException;

    User findByEmail(String email);
    User findById(Long userId);
    void saveUserLoginCookie(String cookieUUID, long userId) throws SQLException;
    void updateUserInformation(String name, String surname, int age, String number, String address, String email, byte[] image, long userId) throws SQLException;
    long getUserId(String email);
    long checkUserExistenceByLoginCookie(String loginCookie) throws SQLException;
}
