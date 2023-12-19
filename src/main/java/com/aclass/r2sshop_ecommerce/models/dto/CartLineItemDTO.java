package com.aclass.r2sshop_ecommerce.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartLineItemDTO {
    private Long id;
    private Long cartId;
    private Long variantProductId;
    private int quantity;
    private Double totalPrice;
    private Timestamp addedDate;
    private Boolean isDeleted;
    private Long orderId;
}
