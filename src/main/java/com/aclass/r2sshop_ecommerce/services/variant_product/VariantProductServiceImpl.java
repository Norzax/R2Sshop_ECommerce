package com.aclass.r2sshop_ecommerce.services.variant_product;

import com.aclass.r2sshop_ecommerce.constants.AppConstants;
import com.aclass.r2sshop_ecommerce.models.dto.VariantProductDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.entity.ProductEntity;
import com.aclass.r2sshop_ecommerce.models.entity.VariantProductEntity;
import com.aclass.r2sshop_ecommerce.repositories.ProductRepository;
import com.aclass.r2sshop_ecommerce.repositories.VariantProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VariantProductServiceImpl implements VariantProductService {

    private final VariantProductRepository variantProductRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public VariantProductServiceImpl(VariantProductRepository variantProductRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.variantProductRepository = variantProductRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseDTO<List<VariantProductDTO>> getVariantProductsByProductId(Long productId) {
        Optional<ProductEntity> optionalProduct = productRepository.findProductWithVariantsById(productId);

        if (optionalProduct.isPresent()) {
            ProductEntity productEntity = optionalProduct.get();
            List<VariantProductEntity> variantProducts = productEntity.getVariantProductEntities();

            List<VariantProductDTO> variantProductDTOs = variantProducts.stream()
                    .map(variantProductEntity -> modelMapper.map(variantProductEntity, VariantProductDTO.class))
                    .collect(Collectors.toList());

            return ResponseDTO.<List<VariantProductDTO>>builder()
                    .status(String.valueOf(HttpStatus.OK.value()))
                    .message("Variant products found for the product")
                    .data(variantProductDTOs)
                    .build();
        } else {
            return ResponseDTO.<List<VariantProductDTO>>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .message("Product not found")
                    .build();
        }
    }

    @Override
    public ResponseDTO<List<VariantProductDTO>> findAll() {
        List<VariantProductEntity> list = variantProductRepository.findAll();

        if (list.isEmpty()) {
            return ResponseDTO.<List<VariantProductDTO>>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .message("Not found list variant products")
                    .build();
        }

        List<VariantProductDTO> listRes = list.stream()
                .map(variantProductEntity -> modelMapper.map(variantProductEntity, VariantProductDTO.class))
                .collect(Collectors.toList());

        return ResponseDTO.<List<VariantProductDTO>>builder()
                .status(String.valueOf(HttpStatus.OK.value()))
                .message("Found list variant products")
                .data(listRes)
                .build();
    }

    @Override
    public ResponseDTO<VariantProductDTO> findById(Long id) {
        Optional<VariantProductEntity> optionalVariantProduct = variantProductRepository.findById(id);

        if (optionalVariantProduct.isPresent()) {
            VariantProductDTO variantProductDTO = modelMapper.map(optionalVariantProduct.get(), VariantProductDTO.class);
            return ResponseDTO.<VariantProductDTO>builder()
                    .status(String.valueOf(HttpStatus.OK.value()))
                    .message("Variant product found")
                    .data(variantProductDTO)
                    .build();
        } else {
            return ResponseDTO.<VariantProductDTO>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .message("Variant product not found")
                    .build();
        }
    }

    @Override
    public ResponseDTO<VariantProductDTO> create(VariantProductDTO variantProductDTO) {
        // Map the VariantProductDTO to VariantProductEntity
        VariantProductEntity variantProductEntity = modelMapper.map(variantProductDTO, VariantProductEntity.class);

        // Save the entity
        VariantProductEntity savedVariantProduct = variantProductRepository.save(variantProductEntity);

        // Map the saved entity back to DTO
        VariantProductDTO savedVariantProductDTO = modelMapper.map(savedVariantProduct, VariantProductDTO.class);

        return ResponseDTO.<VariantProductDTO>builder()
                .status(String.valueOf(HttpStatus.CREATED.value()))
                .message("Variant product created " + AppConstants.SUCCESS_MESSAGE)
                .data(savedVariantProductDTO)
                .build();
    }

    @Override
    public ResponseDTO<VariantProductDTO> update(Long id, VariantProductDTO variantProductDTO) {
        try {
            Optional<VariantProductEntity> optionalVariantProduct = variantProductRepository.findById(id);

            if (optionalVariantProduct.isPresent()) {
                VariantProductEntity existingVariantProduct = optionalVariantProduct.get();

                // Update the fields with new values from updatedVariantProductDTO
                existingVariantProduct.setColor(variantProductDTO.getColor());
                existingVariantProduct.setSize(variantProductDTO.getSize());
                existingVariantProduct.setModel(variantProductDTO.getModel());
                existingVariantProduct.setPrice(variantProductDTO.getPrice());

                // Save the updated variant product
                VariantProductEntity updatedVariantProduct = variantProductRepository.save(existingVariantProduct);

                VariantProductDTO updatedVariantProductDTO = modelMapper.map(updatedVariantProduct, VariantProductDTO.class);

                return ResponseDTO.<VariantProductDTO>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message("Variant product updated " + AppConstants.SUCCESS_MESSAGE)
                        .data(updatedVariantProductDTO)
                        .build();
            } else {
                return ResponseDTO.<VariantProductDTO>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message("Variant product not found for update")
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<VariantProductDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to update variant product")
                    .build();
        }
    }

    @Override
    public ResponseDTO<Void> delete(Long id) {
        try {
            Optional<VariantProductEntity> optionalVariantProduct = variantProductRepository.findById(id);

            if (optionalVariantProduct.isPresent()) {
                variantProductRepository.deleteById(id);

                return ResponseDTO.<Void>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message("Variant product deleted " + AppConstants.SUCCESS_MESSAGE)
                        .build();
            } else {
                return ResponseDTO.<Void>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message("Variant product not found for deletion")
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<Void>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to delete variant product")
                    .build();
        }
    }
}
