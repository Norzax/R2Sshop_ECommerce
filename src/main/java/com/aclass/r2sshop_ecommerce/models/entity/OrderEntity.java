package com.aclass.r2sshop_ecommerce.models.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "delivery_Time")
    private Timestamp deliveryTime;

    @Column(name = "total_Price",length = 8)
    private Float totalPrice;
    @OneToMany(mappedBy = "Oder_id")
    private CartLineItemEntity cartLineItem;

}
