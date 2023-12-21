package com.aclass.r2sshop_ecommerce.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Role")
public class RoleDTO {
        @Schema(hidden = true)
        private long id;
        private String name;
        private String description;
}
