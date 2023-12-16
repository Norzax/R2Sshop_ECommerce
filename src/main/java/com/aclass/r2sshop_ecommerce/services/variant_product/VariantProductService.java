package com.aclass.r2sshop_ecommerce.services.variant_product;

import com.aclass.r2sshop_ecommerce.models.dto.VariantProductDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.Service;

import java.util.List;

public interface VariantProductService extends Service<VariantProductDTO>  {
    ResponseDTO<List<VariantProductDTO>> getVariantProductsByProductId(Long productId);

}
