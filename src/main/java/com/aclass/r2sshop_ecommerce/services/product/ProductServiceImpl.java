package com.aclass.r2sshop_ecommerce.services.product;

import com.aclass.r2sshop_ecommerce.constants.AppConstants;
import com.aclass.r2sshop_ecommerce.exceptions.ProductNotFoundException;
import com.aclass.r2sshop_ecommerce.models.dto.ProductDTO;
import com.aclass.r2sshop_ecommerce.models.dto.RoleDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.entity.ProductEntity;
import com.aclass.r2sshop_ecommerce.repositories.ProductRepository;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDTO getProductById(Long productId) {
        Optional<ProductEntity> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            ProductEntity productEntity = optionalProduct.get();
            return modelMapper.map(productEntity, ProductDTO.class);
        } else {
            try {
                throw new ProductNotFoundException("Product not found with id: " + productId);
            } catch (ProductNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public ResponseDTO<List<ProductDTO>> findProductsByCategoryId(Long categoryId, int page, int pageSize) {
        if (page < 1 || pageSize < 1) {
            return ResponseDTO.<List<ProductDTO>>builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                    .message("Invalid page or pageSize values")
                    .build();
        }

        try {
            int startIndex = (page - 1) * pageSize;
            List<ProductEntity> productList = productRepository.findProductsByCategoryIdWithPaging(categoryId, startIndex, pageSize);
            List<ProductDTO> productDTOList = productList.stream()
                    .map(productEntity -> modelMapper.map(productEntity, ProductDTO.class))
                    .collect(Collectors.toList());

            int totalProducts = (int) productRepository.countProductsByCategoryId(categoryId);
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

            return ResponseDTO.<List<ProductDTO>>builder()
                    .status(String.valueOf(HttpStatus.OK.value()))
                    .message("Found list products")
                    .data(productDTOList)
                    .metadata(Map.of("totalPages", totalPages))
                    .build();
        } catch (Exception e) {
            return ResponseDTO.<List<ProductDTO>>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to retrieve products")
                    .build();
        }
    }


    @Override
    public ResponseDTO<List<ProductDTO>> findAll() {
        List<ProductEntity> list = productRepository.findAll();

        if (list.isEmpty()) {
            return ResponseDTO.<List<ProductDTO>>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND))
                    .message(AppConstants.FOUND_LIST_MESSAGE)
                    .build();
        }

        List<ProductDTO> listRes = list.stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductDTO.class))
                .collect(Collectors.toList());

        return ResponseDTO.<List<ProductDTO>>builder()
                .status(String.valueOf(HttpStatus.OK))
                .message(AppConstants.FOUND_LIST_MESSAGE)
                .data(listRes)
                .build();
    }

    @Override
    public ResponseDTO<ProductDTO> findById(Long id) {
        Optional<ProductEntity> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            ProductDTO productDTO = modelMapper.map(optionalProduct.get(), ProductDTO.class);
            return ResponseDTO.<ProductDTO>builder()
                    .status(String.valueOf(HttpStatus.OK.value()))
                    .message(AppConstants.FOUND_MESSAGE)
                    .data(productDTO)
                    .build();
        } else {
            return ResponseDTO.<ProductDTO>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .message(AppConstants.NOT_FOUND_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<ProductDTO> create(ProductDTO productDTO) {
        try {
            ProductEntity productEntity = modelMapper.map(productDTO, ProductEntity.class);
            ProductEntity savedProduct = productRepository.save(productEntity);
            ProductDTO savedProductDTO = modelMapper.map(savedProduct, ProductDTO.class);

            return ResponseDTO.<ProductDTO>builder()
                    .status(String.valueOf(HttpStatus.CREATED.value()))
                    .message("Product created " + AppConstants.SUCCESS_MESSAGE)
                    .data(savedProductDTO)
                    .build();
        }catch (Exception e) {
            return ResponseDTO.<ProductDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.CREATE_FAILED_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<ProductDTO> update(Long id, ProductDTO productDTO) {
        try {
            Optional<ProductEntity> optionalProduct = productRepository.findById(id);

            if (optionalProduct.isPresent()) {
                ProductEntity existingProduct = optionalProduct.get();

                // Update the fields with new values from updatedProductDTO
                existingProduct.setName(productDTO.getName());

                ProductEntity updatedProduct = productRepository.save(existingProduct);

                ProductDTO updatedProductDTO = modelMapper.map(updatedProduct, ProductDTO.class);

                return ResponseDTO.<ProductDTO>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message(AppConstants.UPDATE_SUCCESS_MESSAGE)
                        .data(updatedProductDTO)
                        .build();
            } else {
                return ResponseDTO.<ProductDTO>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message(AppConstants.UPDATE_NOT_FOUND_MESSAGE)
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<ProductDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.UPDATE_FAILED_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<Void> delete(Long id) {
        try {
            Optional<ProductEntity> optionalProduct = productRepository.findById(id);

            if (optionalProduct.isPresent()) {
                productRepository.deleteById(id);

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
