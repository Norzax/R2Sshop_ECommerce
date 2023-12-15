package com.aclass.r2sshop_ecommerce.models.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import javax.management.relation.Role;

public class Role_User {
    @ManyToOne()
    @JoinColumn(name = "Role_id")
    private RoleEntity role;

    @ManyToOne()
    @JoinColumn(name = "User_id")
    private UserEntity user;
}
