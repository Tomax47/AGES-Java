package org.AGES.repository.user;

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
    public int register(RegisterForm registerForm) throws SQLException {

        if (isNotNullOrEmptyString(registerForm.getName()) && isNotNullOrEmptyString(registerForm.getSurname()) &&
                isNotNullOrEmptyString(registerForm.getEmail()) &&
                isNotNullOrEmptyString(registerForm.getPassword())) {

            User user = User.builder()
                    .name(registerForm.getName())
                    .surname(registerForm.getSurname())
                    .email(registerForm.getEmail())
                    .password(passwordEncoder.encode(registerForm.getPassword()))
                    .build();

            userCRUDRepo.save(user);
            return 1;
        } else {
            System.out.println("NULL VALUES PROHIBITED THE USER FROM BEING REGISTERED!");
            return 0;
        }
    }

    private boolean isNotNullOrEmptyString(String string) {
        return !string.equals(null) && string.length() > 4;
    }

}
