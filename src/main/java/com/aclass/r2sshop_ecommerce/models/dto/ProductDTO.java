package com.aclass.r2sshop_ecommerce.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Product")
public class ProductDTO {
    @Schema(hidden = true)
    private Long id;
    private String name;
    private Long categoryId;
}
