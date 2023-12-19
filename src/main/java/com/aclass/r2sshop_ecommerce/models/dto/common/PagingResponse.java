package com.aclass.r2sshop_ecommerce.models.dto.common;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingResponse<T> {
    private int page;
    private int pageSize;
    private List<T> data;
    private int totalPage;
    private int totalRecord;
    private String status;
    private String message;
}
