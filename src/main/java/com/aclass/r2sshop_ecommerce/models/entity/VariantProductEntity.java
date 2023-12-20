package com.aclass.r2sshop_ecommerce.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "variant_product")
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
  
    @Column(name = "price")
    private long price;
  
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference
    private ProductEntity product;

    @OneToMany(mappedBy = "variantProduct")
    private List<CartLineItemEntity> cartLineItemEntities;
}

