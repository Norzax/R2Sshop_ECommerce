package com.aclass.r2sshop_ecommerce.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.sql.Timestamp;
import java.util.List;

public class CartLineItemEntity {
    @ManyToOne()
    @JoinColumn(name = "Cart_id")
    private CartEntity Cart;

    @ManyToOne()
    @JoinColumn(name = "Variant_Product_id")
    private VariantProductEntity variantProduct;

    @Column(name = "quantity")
    private int quantity;

    @Column(name="total_Price")
    private Float totalPrice;

    @Column(name = "added_Date")
    private Timestamp addedDate;

    @Column(name = "is_Deleted")
    private Boolean isDeleted;
    @ManyToOne()
    @JoinColumn(name = "Oder_id")
    private OrderEntity order;


   /* @OneToMany(mappedBy = "category")
    private List<Course> courses;
    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;*/
}
