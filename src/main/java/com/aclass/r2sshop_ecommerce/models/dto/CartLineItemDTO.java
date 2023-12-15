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
    private int quanlity;
    private Float totalPrice;
    private Timestamp addedDate;
    private Boolean isDeleted;
}
