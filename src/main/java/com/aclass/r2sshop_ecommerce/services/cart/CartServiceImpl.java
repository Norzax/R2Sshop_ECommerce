package com.aclass.r2sshop_ecommerce.services.cart;

import com.aclass.r2sshop_ecommerce.models.dto.CartDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{
    @Override
    public ResponseDTO<List<CartDTO>> findAll() {
        return null;
    }

    @Override
    public ResponseDTO<CartDTO> findById(Long id) {
        return null;
    }

    @Override
    public ResponseDTO<CartDTO> create(CartDTO dto) {
        return null;
    }

    @Override
    public ResponseDTO<CartDTO> update(Long id, CartDTO dto) {
        return null;
    }

    @Override
    public ResponseDTO<Void> delete(Long id) {
        return null;
    }
}
