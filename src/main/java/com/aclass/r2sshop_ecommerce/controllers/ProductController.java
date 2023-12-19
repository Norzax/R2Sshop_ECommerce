package com.aclass.r2sshop_ecommerce.controllers;

import com.aclass.r2sshop_ecommerce.models.dto.ProductDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.PagingRequest;
import com.aclass.r2sshop_ecommerce.models.dto.common.PagingResponse;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.product.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO<List<ProductDTO>>> findAll() {
        ResponseDTO<List<ProductDTO>> response = productService.findAll();
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ResponseDTO<ProductDTO>> findById(@PathVariable Long id) {
        ResponseDTO<ProductDTO> response = productService.findById(id);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<ProductDTO>> create(@Valid @RequestBody ProductDTO productDTO) {
        ResponseDTO<ProductDTO> response = productService.create(productDTO);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO<ProductDTO>> update(@PathVariable Long id, @Validated @RequestBody ProductDTO updatedProductDTO) {
        ResponseDTO<ProductDTO> response = productService.update(id, updatedProductDTO);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long id) {
        ResponseDTO<Void> response = productService.delete(id);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @GetMapping("/category/{category_id}/{page}/{pageSize}")
    public ResponseEntity<PagingResponse<ProductDTO>> getProductsByCategoryId(
            @PathVariable Long category_id,
            @PathVariable String page,
            @PathVariable String pageSize) {
        PagingRequest request = new PagingRequest();
        request.setPage(Integer.parseInt(page));
        request.setPageSize(Integer.parseInt(pageSize));

        PagingResponse<ProductDTO> response = productService.findProductsByCategoryId(category_id, request);

        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(httpStatus).body(response);
    }

    @GetMapping("/getProductById/{id}")
    public ResponseEntity<ResponseDTO<ProductDTO>> getProductById(@PathVariable Long id) {
        ProductDTO productDTO = productService.getProductById(id);
        return ResponseEntity.ok(
                ResponseDTO.<ProductDTO>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message("Product found")
                        .data(productDTO)
                        .build()
        );
    }
}

