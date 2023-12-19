package com.aclass.r2sshop_ecommerce.models.dto.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResquestDTO {
    private String username;
    private String password;
}
