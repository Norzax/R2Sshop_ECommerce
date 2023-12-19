package com.aclass.r2sshop_ecommerce.models.dto.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingRequest {

    @NotNull
    private int page;
    @NotNull
    private int pageSize;

    @NotBlank
    @Pattern(regexp = "ASC|DESC")
    private String orderBy;
}
