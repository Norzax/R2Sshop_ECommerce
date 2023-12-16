package com.aclass.r2sshop_ecommerce.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity()
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "delivery_time")
    private Timestamp deliveryTime;

    @Column(name = "total_price",length = 8)
    private Float totalPrice;

    @OneToMany(mappedBy = "order")
    private List<CartLineItemEntity> cartLineItemEntities;
}
