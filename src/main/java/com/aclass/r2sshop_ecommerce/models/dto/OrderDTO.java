package com.aclass.r2sshop_ecommerce.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "Order")
public class OrderDTO {
    @Schema(hidden = true)
    private Long id;
    private String address;
    private Timestamp deliveryTime;
    private Double totalPrice;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CartLineItemDTO> cartLineItems;
}