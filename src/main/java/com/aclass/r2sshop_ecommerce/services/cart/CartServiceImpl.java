package com.aclass.r2sshop_ecommerce.services.cart;

import com.aclass.r2sshop_ecommerce.constants.AppConstants;
import com.aclass.r2sshop_ecommerce.models.dto.CartDTO;
import com.aclass.r2sshop_ecommerce.models.dto.ProductDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.entity.CartEntity;
import com.aclass.r2sshop_ecommerce.models.entity.ProductEntity;
import com.aclass.r2sshop_ecommerce.repositories.CartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseDTO<List<CartDTO>> findAll() {
        List<CartEntity> list = cartRepository.findAll();

        List<CartDTO> listRes = list.stream()
                .map(cartEntity -> modelMapper.map(cartEntity, CartDTO.class))
                .collect(Collectors.toList());

        if(list.size() > 0) {
            return ResponseDTO.<List<CartDTO>>builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Found list cart")
                    .data(listRes)
                    .build();
        }

        return ResponseDTO.<List<CartDTO>>builder()
                .status(String.valueOf(HttpStatus.NOT_FOUND))
                .message("Not found list cart")
                .build();
    }

    @Override
    public ResponseDTO<CartDTO> findById(Long id) {
        Optional<CartEntity> cartEntity  = cartRepository.findById(id);

        if(cartEntity.isPresent()){
            return ResponseDTO.<CartDTO>builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Found cart")
                    .data(modelMapper.map(cartEntity.get(), CartDTO.class))
                    .build();
        }

        return ResponseDTO.<CartDTO>builder()
                .status(String.valueOf(HttpStatus.NOT_FOUND))
                .message("Not found cart")
                .build();
    }

    @Override
    public ResponseDTO<CartDTO> create(CartDTO dto) {
        CartEntity newCart = cartRepository.save(modelMapper.map(dto, CartEntity.class));
        if(newCart != null) {
            return ResponseDTO.<CartDTO>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND))
                    .message(AppConstants.SUCCESS_MESSAGE)
                    .build();
        }
        return ResponseDTO.<CartDTO>builder()
                .status(String.valueOf(HttpStatus.NOT_FOUND))
                .message(AppConstants.ERROR_MESSAGE)
                .build();
    }

    @Override
    public ResponseDTO<CartDTO> update(Long id, CartDTO dto) {
        return ResponseDTO.<CartDTO>builder()
                .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                .message("You cannot change cart information")
                .build();
    }

    @Override
    public ResponseDTO<Void> delete(Long id) {
        return ResponseDTO.<Void>builder()
                .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                .message("You cannot delete cart of any user")
                .build();
    }
}
