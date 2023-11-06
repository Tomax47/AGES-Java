package org.AGES.dto;

import lombok.Data;

@Data
public class RegisterForm {
    private String name;
    private String surname;
    private String email;
    private String password;
}
