package com.aclass.r2sshop_ecommerce.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "promotion")
public class PromoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "usage_limit")
    private int usageLimit;

    @Column(name = "is_Enable")
    private Boolean isEnable;

    @Column(name = "discount_percentage")
    private int discountPercentage;

    @Column(name = "description")
    private String description;

    @Column(name = "is_order_discount")
    private boolean isOrderDiscount;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "variant_product_promo",
            joinColumns = @JoinColumn(name = "promo_id"),
            inverseJoinColumns = @JoinColumn(name = "variant_product_id")
    )
    private Set<VariantProductEntity> variantProducts = new HashSet<>();
}