package org.AGES.repository;

import org.AGES.dto.RegisterForm;

import java.sql.SQLException;

public interface UserRegistrationService {
    void register(RegisterForm registerForm) throws SQLException;
}
