package org.AGES.repository.user;

import org.AGES.dto.RegisterForm;

import java.sql.SQLException;

public interface UserRegistrationService {
    int register(RegisterForm registerForm) throws SQLException;
}
