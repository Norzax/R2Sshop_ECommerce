package com.aclass.r2sshop_ecommerce.models.dto.common;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T> {
    private String status;
    private T data;
    private String message;
}
