package com.aclass.r2sshop_ecommerce.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

public class VariantProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="color",length = 50)
    private String color;

    @Column(name="size", length = 50)
    private String size;

    @Column(name="model", length = 100)
    private String model;

    @Column(name = "price",length = 8)
    private Float price;
    @ManyToOne()
    @JoinColumn(name = "Product_id", nullable = false, referencedColumnName = "id")// => category's id
    @JsonBackReference
    private ProductEntity product;
}

