package com.aclass.r2sshop_ecommerce.models.dto.token;

import com.aclass.r2sshop_ecommerce.models.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenGenerated {
    private String refreshToken;
    private Date expiredIn;
    private UserEntity user;
}
