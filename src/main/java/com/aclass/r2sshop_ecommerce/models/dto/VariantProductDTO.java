package com.aclass.r2sshop_ecommerce.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Variant Product")
public class VariantProductDTO {
    @Schema(hidden = true)
    private long id;
    private String color;
    private String size;
    private String model;
    private Long price;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double discountPrice;

    private Long productId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<PromoDTO> promoDTOSet;

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = (discountPrice == null || discountPrice.equals(0.0)) ? null : discountPrice;
    }
}
