package com.aclass.r2sshop_ecommerce.models.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Paging Request")
public class PagingRequest {
    @NotNull
    private int page;

    @NotNull
    private int pageSize;

    @NotBlank
    @Pattern(regexp = "ASC|DESC")
    private String orderBy;
}
