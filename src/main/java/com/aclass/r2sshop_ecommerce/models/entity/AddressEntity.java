package com.aclass.r2sshop_ecommerce.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "address")
    private String address;
    @ManyToOne()
    @JoinColumn(name = "User_id", nullable = false, referencedColumnName = "id")// => category's id
    @JsonBackReference
    private UserEntity user;

}
