package com.aclass.r2sshop_ecommerce.models.dto;

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
public class UserDTO {
    private long id;
    private String username;
    private String password;
    private String email;
    private String fullName;

    private List<AddressDTO> addressDTOList;
    private CartDTO cartDTO;
    private Set<RoleDTO> roleDTOSet;
}

