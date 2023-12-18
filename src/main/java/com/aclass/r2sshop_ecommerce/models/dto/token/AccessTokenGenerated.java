package com.aclass.r2sshop_ecommerce.models.dto.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenGenerated {
    private String accessToken;
    private Date expiredIn;
}

