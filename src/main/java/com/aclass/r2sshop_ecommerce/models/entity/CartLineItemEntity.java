package com.aclass.r2sshop_ecommerce.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "cartline_item")
public class CartLineItemEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference
    private CartEntity cart;

    @ManyToOne
    @JoinColumn(name = "variant_product_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference
    private VariantProductEntity variantProduct;

    @Column(name = "quantity")
    private int quantity;

    @Column(name="total_price")
    private Double totalPrice;

    @Column(name = "added_date")
    private Timestamp addedDate;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference
    private OrderEntity order;
}
