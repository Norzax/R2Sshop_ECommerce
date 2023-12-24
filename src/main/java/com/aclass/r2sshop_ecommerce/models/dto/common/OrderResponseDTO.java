package com.aclass.r2sshop_ecommerce.models.dto.common;

import com.aclass.r2sshop_ecommerce.models.dto.CartLineItemDTO;
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
@Schema(name = "Order Current User")
public class OrderResponseDTO {
    private Long id;
    private String address;
    private Timestamp deliveryTime;
    private Double totalPrice;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double discountPrice;
    private String userInfo;

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = (discountPrice == null || discountPrice.equals(0.0)) ? null : discountPrice;
    }
}
