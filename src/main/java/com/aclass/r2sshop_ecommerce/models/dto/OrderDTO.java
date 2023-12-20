package com.aclass.r2sshop_ecommerce.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private String address;
    private Timestamp deliveryTime;
    private Double totalPrice;
    private List<CartLineItemDTO> cartLineItems;
}