package com.aclass.r2sshop_ecommerce.services.product;

import com.aclass.r2sshop_ecommerce.constants.AppConstants;
import com.aclass.r2sshop_ecommerce.constants.OrderBy;
import com.aclass.r2sshop_ecommerce.exceptions.ProductNotFoundException;
import com.aclass.r2sshop_ecommerce.models.dto.ProductDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.PagingRequest;
import com.aclass.r2sshop_ecommerce.models.dto.common.PagingResponse;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.entity.ProductEntity;
import com.aclass.r2sshop_ecommerce.repositories.ProductRepository;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public PagingResponse<ProductDTO> findProductsByCategoryId(Long categoryId, PagingRequest request) {
        try {
            int pageSize = request.getPageSize();
            int page = request.getPage();
            int startIndex = (page - 1) * pageSize;

            List<ProductEntity> productEntities;

            if (OrderBy.ASC.name().equals(request.getOrderBy())) {
                productEntities = productRepository.searchOrderByCategoryIdDesc(categoryId, pageSize, startIndex);
            } else {
                productEntities = productRepository.searchOrderByCategoryIdAsc(categoryId, pageSize, startIndex);
            }

            int totalRecord = productRepository.getTotalRecordSearch(categoryId);
            int totalPage = (int) Math.ceil((double) totalRecord / pageSize);

            List<ProductDTO> productDTOList = productEntities.stream()
                    .map(productEntity -> modelMapper.map(productEntity, ProductDTO.class))
                    .collect(Collectors.toList());

            PagingResponse<ProductDTO> response;

            if (productDTOList.isEmpty()) {
                response = PagingResponse.<ProductDTO>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message("No products found for the given category.")
                        .build();
            } else {
                response = PagingResponse.<ProductDTO>builder()
                        .data(productDTOList)
                        .page(page)
                        .pageSize(pageSize)
                        .totalPage(totalPage)
                        .totalRecord(totalRecord)
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .build();
            }

            return response;
        } catch (Exception e) {
            return PagingResponse.<ProductDTO>builder()
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
                    .message("Not found list products")
                    .build();
        }

        List<ProductDTO> listRes = list.stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductDTO.class))
                .collect(Collectors.toList());

        return ResponseDTO.<List<ProductDTO>>builder()
                .status(String.valueOf(HttpStatus.OK))
                .message("Found list products")
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
                    .message("Product found")
                    .data(productDTO)
                    .build();
        } else {
            return ResponseDTO.<ProductDTO>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .message("Product not found")
                    .build();
        }
    }

    @Override
    public ResponseDTO<ProductDTO> create(ProductDTO productDTO) {
        // Map the ProductDTO to ProductEntity
        ProductEntity productEntity = modelMapper.map(productDTO, ProductEntity.class);

        // Save the entity
        ProductEntity savedProduct = productRepository.save(productEntity);

        // Map the saved entity back to DTO
        ProductDTO savedProductDTO = modelMapper.map(savedProduct, ProductDTO.class);

        return ResponseDTO.<ProductDTO>builder()
                .status(String.valueOf(HttpStatus.CREATED.value()))
                .message("Product created " + AppConstants.SUCCESS_MESSAGE)
                .data(savedProductDTO)
                .build();
    }

    @Override
    public ResponseDTO<ProductDTO> update(Long id, ProductDTO productDTO) {
        try {
            Optional<ProductEntity> optionalProduct = productRepository.findById(id);

            if (optionalProduct.isPresent()) {
                ProductEntity existingProduct = optionalProduct.get();

                // Update the fields with new values from updatedProductDTO
                existingProduct.setName(productDTO.getName());
                // Add more fields as needed

                // Save the updated product
                ProductEntity updatedProduct = productRepository.save(existingProduct);

                ProductDTO updatedProductDTO = modelMapper.map(updatedProduct, ProductDTO.class);

                return ResponseDTO.<ProductDTO>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message("Product updated" + AppConstants.SUCCESS_MESSAGE)
                        .data(updatedProductDTO)
                        .build();
            } else {
                return ResponseDTO.<ProductDTO>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message("Product not found for update")
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<ProductDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to update product")
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
                        .message("Product deleted " + AppConstants.SUCCESS_MESSAGE)
                        .build();
            } else {
                return ResponseDTO.<Void>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message("Product not found for deletion")
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<Void>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to delete product")
                    .build();
        }
    }
}
