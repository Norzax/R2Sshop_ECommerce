package com.aclass.r2sshop_ecommerce.services.cart_line_item;

import com.aclass.r2sshop_ecommerce.models.dto.CartLineItemDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.entity.CartLineItemEntity;
import com.aclass.r2sshop_ecommerce.repositories.CartLineItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.aclass.r2sshop_ecommerce.constants.AppConstants;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartLineItemServiceImpl implements CartLineItemService {

    private final CartLineItemRepository cartLineItemRepository;
    private final ModelMapper modelMapper;

    public CartLineItemServiceImpl(CartLineItemRepository cartLineItemRepository, ModelMapper modelMapper) {
        this.cartLineItemRepository = cartLineItemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseDTO<List<CartLineItemDTO>> findAll() {
        List<CartLineItemEntity> list = cartLineItemRepository.findAll();

        if (list.isEmpty()) {
            return ResponseDTO.<List<CartLineItemDTO>>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND))
                    .message(AppConstants.NOT_FOUND_LIST_MESSAGE)
                    .build();
        }

        List<CartLineItemDTO> listRes = list.stream()
                .map(cartLineItemEntity -> modelMapper.map(cartLineItemEntity, CartLineItemDTO.class))
                .collect(Collectors.toList());

        return ResponseDTO.<List<CartLineItemDTO>>builder()
                .status(String.valueOf(HttpStatus.OK))
                .message(AppConstants.FOUND_LIST_MESSAGE)
                .data(listRes)
                .build();
    }

    @Override
    public ResponseDTO<CartLineItemDTO> findById(Long id) {
        Optional<CartLineItemEntity> optionalCartLineItem = cartLineItemRepository.findById(id);

        if (optionalCartLineItem.isPresent()) {
            CartLineItemDTO cartLineItemDTO = modelMapper.map(optionalCartLineItem.get(), CartLineItemDTO.class);
            return ResponseDTO.<CartLineItemDTO>builder()
                    .status(String.valueOf(HttpStatus.OK.value()))
                    .message(AppConstants.FOUND_MESSAGE)
                    .data(cartLineItemDTO)
                    .build();
        } else {
            return ResponseDTO.<CartLineItemDTO>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .message(AppConstants.NOT_FOUND_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<CartLineItemDTO> create(CartLineItemDTO cartLineItemDTO) {
        try {
            CartLineItemEntity cartLineItemEntity = modelMapper.map(cartLineItemDTO, CartLineItemEntity.class);
            CartLineItemEntity savedCartLineItem = cartLineItemRepository.save(cartLineItemEntity);
            CartLineItemDTO savedCartLineItemDTO = modelMapper.map(savedCartLineItem, CartLineItemDTO.class);

            return ResponseDTO.<CartLineItemDTO>builder()
                    .status(String.valueOf(HttpStatus.CREATED.value()))
                    .message(AppConstants.CREATE_SUCCESS_MESSAGE)
                    .data(savedCartLineItemDTO)
                    .build();
        } catch (Exception e) {
            return ResponseDTO.<CartLineItemDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.CREATE_FAILED_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<CartLineItemDTO> update(Long id, CartLineItemDTO cartLineItemDTO) {
        try {
            Optional<CartLineItemEntity> optionalCartLineItem = cartLineItemRepository.findById(id);

            if (optionalCartLineItem.isPresent()) {
                CartLineItemEntity existingCartLineItem = optionalCartLineItem.get();

                // Cập nhật thông tin từ DTO
                existingCartLineItem.setQuantity(cartLineItemDTO.getQuantity());
                existingCartLineItem.setTotalPrice(cartLineItemDTO.getTotalPrice());

                CartLineItemEntity updatedCartLineItem = cartLineItemRepository.save(existingCartLineItem);
                CartLineItemDTO updatedCartLineItemDTO = modelMapper.map(updatedCartLineItem, CartLineItemDTO.class);

                return ResponseDTO.<CartLineItemDTO>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message(AppConstants.UPDATE_SUCCESS_MESSAGE)
                        .data(updatedCartLineItemDTO)
                        .build();
            } else {
                return ResponseDTO.<CartLineItemDTO>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message(AppConstants.UPDATE_NOT_FOUND_MESSAGE)
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<CartLineItemDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.UPDATE_FAILED_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<Void> delete(Long id) {
        try {
            Optional<CartLineItemEntity> optionalCartLineItem = cartLineItemRepository.findById(id);

            if (optionalCartLineItem.isPresent()) {
                cartLineItemRepository.deleteById(id);

                return ResponseDTO.<Void>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message(AppConstants.DELETE_SUCCESS_MESSAGE)
                        .build();
            } else {
                return ResponseDTO.<Void>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message(AppConstants.DELETE_NOT_FOUND_MESSAGE)
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<Void>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.DELETE_FAILED_MESSAGE)
                    .build();
        }
    }

}
