package org.AGES.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private int age;
    private String email;
    private String number;
    private String address;
    private byte[] image;
    private String role;

    //TODO: TO USE LATER ON
    public boolean isAdmin() {
        return this.role.equals("Admin");
    }
}