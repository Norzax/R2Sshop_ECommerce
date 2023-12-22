package com.aclass.r2sshop_ecommerce.services.product;

import com.aclass.r2sshop_ecommerce.models.dto.ProductDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.PagingRequest;
import com.aclass.r2sshop_ecommerce.models.dto.common.PagingResponse;
import com.aclass.r2sshop_ecommerce.services.IService;
import org.springframework.stereotype.Service;


@Service
public interface ProductService extends IService<ProductDTO> {
    // Lấy danh sách sản phẩm theo id danh mục với phân trang
    PagingResponse<ProductDTO> findProductsByCategoryId(Long categoryId,PagingRequest request);
}
