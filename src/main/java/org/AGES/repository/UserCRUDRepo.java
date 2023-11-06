package org.AGES.repository;

import org.AGES.model.User;

import java.sql.SQLException;

public interface UserCRUDRepo extends CRUDRepo<User> {
    boolean login(String email, String password) throws SQLException;

    User findByEmail(String email);
}
