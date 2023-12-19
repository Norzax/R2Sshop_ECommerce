package com.aclass.r2sshop_ecommerce.models.dto.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO<R> {
    private String username;
    private String password;
    private String address;
    private String email;
    private String fullName;
}
