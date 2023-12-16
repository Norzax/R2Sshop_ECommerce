package com.aclass.r2sshop_ecommerce.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity()
@Table(name = "cart_line")
public class CartLineItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "cart_id")
    private CartEntity cart;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_price")
    private Float totalPrice;

    @Column(name = "added_date")
    private Timestamp addedDate;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "variant_product_id")
    private VariantProductEntity variantProduct;

    @ManyToOne()
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}
