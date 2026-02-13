package com.auth.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name ="Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userName;
    private String roles;
}
