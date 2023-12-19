package com.aclass.r2sshop_ecommerce.models.dto.common;

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
public class LoginResponseDTO {
    private String refreshToken;
    private String accessToken;
    private Date expiredIn;
    private List<String> roles;
}