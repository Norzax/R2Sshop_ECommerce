package com.aclass.r2sshop_ecommerce.services.product;

import com.aclass.r2sshop_ecommerce.models.dto.ProductDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.IService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService extends IService<ProductDTO> {
    // Lấy danh sách sản phẩm theo id danh mục với phân trang
    ResponseDTO<List<ProductDTO>> findProductsByCategoryId(Long categoryId, int currentPage, int pageSize);
}
