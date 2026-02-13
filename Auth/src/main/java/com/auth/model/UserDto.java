package com.auth.model;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String userName;
    private String email;
    private String roles;



    public UserDto(Long id, String userName, String email, String roles) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.roles=roles;
    }
}
