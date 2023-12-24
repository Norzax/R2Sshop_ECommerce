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
@Schema(name = "Order Request")
public class OrderRequestDTO {
    private String address;
    private String userInfo;
    private String promoCode;
}
