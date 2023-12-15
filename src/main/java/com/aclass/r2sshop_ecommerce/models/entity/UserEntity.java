package com.aclass.r2sshop_ecommerce.models.entity;

import jakarta.persistence.*;

import java.util.List;

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "full_Name")
    private String fullName;

    @Column(name = "email")
    private String email;

}
