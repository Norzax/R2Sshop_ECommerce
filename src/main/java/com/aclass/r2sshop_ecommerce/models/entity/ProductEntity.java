package com.aclass.r2sshop_ecommerce.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", length = 100)
    private String name;

    @ManyToOne()
    @JoinColumn(name = "Category_id", nullable = false, referencedColumnName = "id")// => category's id
    @JsonBackReference
    private CategoryEntity category;
}
