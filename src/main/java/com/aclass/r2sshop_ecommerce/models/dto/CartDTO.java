package com.aclass.r2sshop_ecommerce.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Cart")
public class CartDTO {
        @JsonIgnore
        private Long id;
        private Date createDate;

        @JsonIgnore
        private Long userId;

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private List<CartLineItemDTO> cartLineItemEntities;
}
