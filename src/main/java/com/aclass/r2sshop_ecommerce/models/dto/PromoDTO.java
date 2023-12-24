package com.aclass.r2sshop_ecommerce.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Promote")
public class PromoDTO {
    @JsonIgnore
    private Long id;
    private String code;
    private Date startDate;
    private Date endDate;
    private int usageLimit;
    private int discountPercentage;
    private String description;
    private boolean isEnable;

    @JsonIgnore
    private boolean isOrderDiscount;
    private Long variantProductId;
}
