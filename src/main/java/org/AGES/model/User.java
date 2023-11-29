package org.AGES.model;

import lombok.*;
@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long id;
    private String name;
    private String surname;
    private int age;
    private String number;
    private String address;
    private String email;
    private String password;
    private String role;
}
