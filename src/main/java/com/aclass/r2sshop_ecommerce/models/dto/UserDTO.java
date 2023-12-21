package com.aclass.r2sshop_ecommerce.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "User")
public class UserDTO {
    @Schema(hidden = true)
    private long id;
    private String username;
    private String password;
    private String email;
    private String fullName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<AddressDTO> addressDTOList;
    private CartDTO cartDTO;
    private Set<RoleDTO> roleDTOSet;
}

