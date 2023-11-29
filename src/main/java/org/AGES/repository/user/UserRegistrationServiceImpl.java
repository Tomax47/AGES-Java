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

        System.out.println("PASSWORD IS STRONG -> "+ PasswordIsLongEnough(registerForm.getPassword()));
        //Checking the correctness of the inputted data
        if (isNotNullOrEmptyString(registerForm.getName()) && isNotNullOrEmptyString(registerForm.getSurname()) &&
                isNotNullOrEmptyString(registerForm.getEmail()) &&
                isNotNullOrEmptyString(registerForm.getPassword())) {

            if (PasswordIsLongEnough(registerForm.getPassword())) {
                User user = User.builder()
                        .name(registerForm.getName())
                        .surname(registerForm.getSurname())
                        .email(registerForm.getEmail())
                        .password(passwordEncoder.encode(registerForm.getPassword()))
                        .build();

                int isRegistered = userCRUDRepo.save(user);

                if (isRegistered == 1) {
                    //Registered User
                    return 1;
                } else if (isRegistered == 2) {
                    //Blacklisted Email
                    return 2;
                } else {
                    //Failure
                    return 0;
                }
            } else {
                System.out.println("PASSWORD IS WEAK!");
                return 3;
            }

        } else {
            System.out.println("NULL VALUES PROHIBITED THE USER FROM BEING REGISTERED!");
            return 0;
        }
    }

    private boolean isNotNullOrEmptyString(String string) {
        return !string.isBlank() && string.length() >= 4;
    }
    private boolean PasswordIsLongEnough(String password) {
        return password.length() >= 6;
    }
}
