package com.aclass.r2sshop_ecommerce.controllers;

import com.aclass.r2sshop_ecommerce.models.dto.ProductDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDTO<List<ProductDTO>>> findAll() {
        ResponseDTO<List<ProductDTO>> response = productService.findAll();
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        return ResponseEntity.status(httpStatus).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ProductDTO>> findById(@PathVariable Long id) {
        ResponseDTO<ProductDTO> response = productService.findById(id);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PostMapping("/")
    public ResponseEntity<ResponseDTO<ProductDTO>> create(@Validated @RequestBody ProductDTO productDTO) {
        ResponseDTO<ProductDTO> response = productService.create(productDTO);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ProductDTO>> update(@PathVariable Long id, @Validated @RequestBody ProductDTO updatedProductDTO) {
        ResponseDTO<ProductDTO> response = productService.update(id, updatedProductDTO);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        return ResponseEntity.status(httpStatus).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long id) {
        ResponseDTO<Void> response = productService.delete(id);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        return ResponseEntity.status(httpStatus).body(response);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ResponseDTO<List<ProductDTO>>> getProductsByCategoryId(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        try {
            ResponseDTO<List<ProductDTO>> responseDTO = productService.findProductsByCategoryId(categoryId, page, pageSize);

            return ResponseEntity.ok(
                    ResponseDTO.<List<ProductDTO>>builder()
                            .status(responseDTO.getStatus())
                            .message(responseDTO.getMessage())
                            .data(responseDTO.getData())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDTO.<List<ProductDTO>>builder()
                            .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                            .message("Failed to retrieve products")
                            .build()
                    );
        }
    }
}

