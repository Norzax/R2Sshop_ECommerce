package com.aclass.r2sshop_ecommerce.models.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Register Response")
public class RegisterResponseDTO {
    private String username;
    private String address;
    private String email;
    private String fullName;
}
