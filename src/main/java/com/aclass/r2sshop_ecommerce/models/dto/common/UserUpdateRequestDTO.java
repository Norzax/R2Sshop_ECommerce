package com.aclass.r2sshop_ecommerce.models.dto.common;

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
@Schema(name = "User Update Request")
public class UserUpdateRequestDTO {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
    private String email;
    private String fullName;
    private List<AddressUpdateRequestDTO> addressesUpdate;
}
