package com.aclass.r2sshop_ecommerce.models.dto;

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
public class CartDTO {
        private Long id;
        private Date createDate;
        private Long userId;

        private List<CartLineItemDTO> cartLineItemEntities;
}
