package com.aclass.r2sshop_ecommerce.services.cart;

import com.aclass.r2sshop_ecommerce.models.dto.CartDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.IService;
import org.springframework.stereotype.Service;

@Service
public interface CartService extends IService<CartDTO> {
    ResponseDTO<CartDTO> findCartByUserId(Long userId);
    public ResponseDTO<CartDTO> getUserCart(Long userId);
    public ResponseDTO<CartDTO> findCartForCurrentUser();
    public ResponseDTO<CartDTO> addProductToCart(Long productId, int quantity);
}
