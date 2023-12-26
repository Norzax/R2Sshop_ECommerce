package com.aclass.r2sshop_ecommerce.services.promote;

import com.aclass.r2sshop_ecommerce.constants.AppConstants;
import com.aclass.r2sshop_ecommerce.models.dto.PromoDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.entity.PromoEntity;
import com.aclass.r2sshop_ecommerce.models.entity.VariantProductEntity;
import com.aclass.r2sshop_ecommerce.repositories.PromoRepository;
import com.aclass.r2sshop_ecommerce.repositories.VariantProductRepository;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PromoteServiceImpl implements PromoteService{
    private final PromoRepository promoRepository;
    private final VariantProductRepository variantProductRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PromoteServiceImpl(PromoRepository promoRepository, VariantProductRepository variantProductRepository, ModelMapper modelMapper) {
        this.promoRepository = promoRepository;
        this.variantProductRepository = variantProductRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseDTO<List<PromoDTO>> findAll() {
        List<PromoEntity> list = promoRepository.findAll();

        if (list.isEmpty()) {
            return ResponseDTO.<List<PromoDTO>>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND))
                    .message(AppConstants.NOT_FOUND_LIST_MESSAGE)
                    .build();
        }

        List<PromoDTO> listRes = list.stream()
                .map(PromoEntity -> modelMapper.map(PromoEntity, PromoDTO.class))
                .collect(Collectors.toList());

        return ResponseDTO.<List<PromoDTO>>builder()
                .status(String.valueOf(HttpStatus.OK))
                .message(AppConstants.FOUND_LIST_MESSAGE)
                .data(listRes)
                .build();
    }

    @Override
    public ResponseDTO<PromoDTO> findById(Long id) {
        Optional<PromoEntity> optionalPromo = promoRepository.findById(id);

        if (optionalPromo.isPresent()) {
            PromoDTO promoDTO = modelMapper.map(optionalPromo.get(), PromoDTO.class);
            return ResponseDTO.<PromoDTO>builder()
                    .status(String.valueOf(HttpStatus.OK.value()))
                    .message(AppConstants.FOUND_MESSAGE)
                    .data(promoDTO)
                    .build();
        } else {
            return ResponseDTO.<PromoDTO>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .message(AppConstants.NOT_FOUND_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<PromoDTO> create(PromoDTO dto) {
        boolean isCodeExists = promoRepository.existsByCode(dto.getCode());
        if (isCodeExists) {
            return ResponseDTO.<PromoDTO>builder()
                    .status(AppConstants.ERROR_STATUS)
                    .message("Error: Promo with the same code already exists.")
                    .build();
        }

        boolean is_order_discount = dto.getVariantProductId() == null || dto.getVariantProductId() == 0;

        PromoEntity promoEntity = PromoEntity.builder()
                .code(dto.getCode())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .isEnable(true)
                .discountPercentage(dto.getDiscountPercentage())
                .description(dto.getDescription())
                .isOrderDiscount(is_order_discount)
                .variantProducts(new HashSet<>())
                .build();

        if (dto.getUsageLimit() == 0 && !is_order_discount) {
            promoEntity.setUsageLimit(-1);
        } else if(dto.getUsageLimit() == 0 && is_order_discount){
            promoEntity.setUsageLimit(10);
        } else{
            promoEntity.setUsageLimit(dto.getUsageLimit());
        }

        if (!is_order_discount) {
            Optional<VariantProductEntity> optionalVariantProduct = variantProductRepository.findById(dto.getVariantProductId());
            if (optionalVariantProduct.isEmpty()) {
                return ResponseDTO.<PromoDTO>builder()
                        .status(AppConstants.ERROR_STATUS)
                        .message("Error: VariantProduct with given ID not found.")
                        .build();
            }

            VariantProductEntity variantProduct = optionalVariantProduct.get();

            boolean exists = promoRepository.existsByVariantProductsIdAndIsEnableTrue(variantProduct.getId());
            if (exists) {
                return ResponseDTO.<PromoDTO>builder()
                        .status(AppConstants.ERROR_STATUS)
                        .message("Error: VariantProduct already linked to an active promo.")
                        .build();
            }

            promoEntity.getVariantProducts().add(variantProduct);
            variantProduct.getPromos().add(promoEntity);
        }

        try {
            PromoEntity savedPromo = promoRepository.save(promoEntity);
            PromoDTO createdPromoDTO = modelMapper.map(savedPromo, PromoDTO.class);
            return ResponseDTO.<PromoDTO>builder()
                    .status(AppConstants.SUCCESS_STATUS)
                    .message(AppConstants.SUCCESS_MESSAGE)
                    .data(createdPromoDTO)
                    .build();
        } catch (Exception e) {
            return ResponseDTO.<PromoDTO>builder()
                    .status(AppConstants.ERROR_STATUS)
                    .message("Error creating promo: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDTO<PromoDTO> update(Long id, PromoDTO dto) {
        if (id == null) {
            return ResponseDTO.<PromoDTO>builder()
                    .status(AppConstants.ERROR_STATUS)
                    .message("Error: ID cannot be null.")
                    .build();
        }
        Optional<PromoEntity> optionalPromo = promoRepository.findById(id);

        if (optionalPromo.isPresent()) {
            PromoEntity existingPromo = optionalPromo.get();

            boolean isCodeExists = promoRepository.existsByCode(dto.getCode());
            if (isCodeExists && !existingPromo.getCode().equals(dto.getCode())) {
                return ResponseDTO.<PromoDTO>builder()
                        .status(AppConstants.ERROR_STATUS)
                        .message("Error: Promo with the same code already exists.")
                        .build();
            }

            existingPromo.setId(id);
            existingPromo.setCode(dto.getCode());
            existingPromo.setStartDate(dto.getStartDate());
            existingPromo.setEndDate(dto.getEndDate());
            existingPromo.setIsEnable(dto.isEnable());
            existingPromo.setUsageLimit(dto.getUsageLimit());
            existingPromo.setDiscountPercentage(dto.getDiscountPercentage());
            existingPromo.setDescription(dto.getDescription());
            existingPromo.setOrderDiscount((dto.getVariantProductId() == null || dto.getVariantProductId() == 0) ? true : false);
            if(!existingPromo.isOrderDiscount()){
                Optional<VariantProductEntity> optionalVariantProduct = variantProductRepository.findById(dto.getVariantProductId());
                VariantProductEntity variantProduct = optionalVariantProduct.get();
//                boolean exists = promoRepository.existsByVariantProductsIdAndIsEnableTrue(dto.getVariantProductId());
//                if (exists) {
//                    return ResponseDTO.<PromoDTO>builder()
//                            .status(AppConstants.ERROR_STATUS)
//                            .message("Error: VariantProduct already linked to an active promo.")
//                            .build();
//                }

                existingPromo.getVariantProducts().add(variantProduct);
                variantProduct.getPromos().add(existingPromo);
            } else {
//                boolean exists = promoRepository.existsByVariantProductsIdAndIsEnableTrue(dto.getVariantProductId());
//                if (exists) {
//                    return ResponseDTO.<PromoDTO>builder()
//                            .status(AppConstants.ERROR_STATUS)
//                            .message("Error: VariantProduct already linked to an active promo.")
//                            .build();
//                }
                existingPromo.getVariantProducts().clear();
            }

            try {
                PromoEntity updatedPromo = promoRepository.save(existingPromo);
                PromoDTO updatedPromoDTO = modelMapper.map(updatedPromo, PromoDTO.class);
                return ResponseDTO.<PromoDTO>builder()
                        .status(AppConstants.SUCCESS_STATUS)
                        .message(AppConstants.SUCCESS_MESSAGE)
                        .data(updatedPromoDTO)
                        .build();
            } catch (Exception e) {
                return ResponseDTO.<PromoDTO>builder()
                        .status(AppConstants.ERROR_STATUS)
                        .message("Error updating promo: " + e.getMessage())
                        .build();
            }
        } else {
            return ResponseDTO.<PromoDTO>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .message(AppConstants.NOT_FOUND_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<Void> delete(Long id) {
        Optional<PromoEntity> optionalPromo = promoRepository.findById(id);

        if (optionalPromo.isPresent()) {
            PromoEntity promoToDelete = optionalPromo.get();
            Set<VariantProductEntity> variantProducts = promoToDelete.getVariantProducts();

            if (variantProducts != null && !variantProducts.isEmpty()) {
                for (VariantProductEntity variantProduct : variantProducts) {
                    promoToDelete.getVariantProducts().remove(variantProduct);
                    promoRepository.delete(promoToDelete);
                }
            }

            promoRepository.delete(promoToDelete);

            return ResponseDTO.<Void>builder()
                    .status(AppConstants.SUCCESS_STATUS)
                    .message(AppConstants.SUCCESS_MESSAGE)
                    .build();
        } else {
            return ResponseDTO.<Void>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .message(AppConstants.NOT_FOUND_MESSAGE)
                    .build();
        }
    }
}
