package com.aclass.r2sshop_ecommerce.models.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Address Update Request")
public class AddressUpdateRequestDTO {
    private String oldAddress;
    private String newAddress;
}
