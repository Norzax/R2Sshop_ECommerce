package com.aclass.r2sshop_ecommerce.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VariantProductDTO {
    private long id;
    private String color;
    private String size;
    private String model;
    private Long price;
}
