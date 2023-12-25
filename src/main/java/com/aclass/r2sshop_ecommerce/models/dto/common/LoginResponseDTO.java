package com.aclass.r2sshop_ecommerce.models.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Login Response")
public class LoginResponseDTO {
    private String refreshToken;

    private String accessToken;

    private Date expiredIn;

    private List<String> roles;
}