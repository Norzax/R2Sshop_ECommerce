package com.aclass.r2sshop_ecommerce.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "create_date")
    private Date createDate;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    @JsonIgnore
    private UserEntity user;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER)
    private List<CartLineItemEntity> cartLineItemEntities;
}
