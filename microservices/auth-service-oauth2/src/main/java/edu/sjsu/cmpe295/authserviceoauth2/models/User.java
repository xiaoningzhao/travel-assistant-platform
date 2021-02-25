package edu.sjsu.cmpe295.authserviceoauth2.models;

import lombok.Data;

@Data
public class User {
    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String address;

    private String phone;

    private String role;

    private Boolean active;

    private String token;
}
