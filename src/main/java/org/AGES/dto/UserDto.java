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
    private String email;
    private String password;
    private String role;

    //TODO: TO SUE LATER ON
    public boolean isAdmin() {
        return this.role.equals("Admin");
    }
}