package com.aclass.r2sshop_ecommerce.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Cart Line Item")
public class CartLineItemDTO {
    @JsonIgnore
    private Long id;

    @JsonIgnore
    private Long cartId;
    private VariantProductDTO variantProductDTO;
    private int quantity;
    private Double totalPrice;
    private Timestamp addedDate;

    @JsonIgnore
    private Boolean isDeleted;

    @JsonIgnore
    private Long orderId;
}
