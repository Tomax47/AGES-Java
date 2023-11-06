package org.AGES.repository;

import org.AGES.dto.RegisterForm;
import org.AGES.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;

public class UserRegistrationServiceImpl implements UserRegistrationService{

    private UserCRUDRepo userCRUDRepo;
    private PasswordEncoder passwordEncoder;


    public UserRegistrationServiceImpl(UserCRUDRepo userCRUDRepo){
        this.userCRUDRepo = userCRUDRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    @Override
    public void register(RegisterForm registerForm) throws SQLException {
        User user = User.builder()
                .name(registerForm.getName())
                .surname(registerForm.getSurname())
                .email(registerForm.getEmail())
                .password(passwordEncoder.encode(registerForm.getPassword()))
                .build();

        userCRUDRepo.save(user);
    }
}
