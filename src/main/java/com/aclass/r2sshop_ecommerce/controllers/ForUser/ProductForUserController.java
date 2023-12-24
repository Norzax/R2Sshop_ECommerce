package com.aclass.r2sshop_ecommerce.controllers.ForUser;

import com.aclass.r2sshop_ecommerce.models.dto.ProductDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.PagingRequest;
import com.aclass.r2sshop_ecommerce.models.dto.common.PagingResponse;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.product.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "2. User: <Product>" )
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/user/product")
public class ProductForUserController {

    private final ProductService productService;

    @Autowired
    public ProductForUserController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO<List<ProductDTO>>> findAll() {
        ResponseDTO<List<ProductDTO>> response = productService.findAll();
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @GetMapping("/find-id/{id}")
    public ResponseEntity<ResponseDTO<ProductDTO>> findById(@PathVariable Long id) {
        ResponseDTO<ProductDTO> response = productService.findById(id);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @GetMapping("/category/{categoryId}/{page}/{pageSize}")
    public ResponseEntity<PagingResponse<ProductDTO>> getProductsByCategoryId(
            @PathVariable Long categoryId,
            @PathVariable String page,
            @PathVariable String pageSize) {
        PagingRequest request = new PagingRequest();
        request.setPage(Integer.parseInt(page));
        request.setPageSize(Integer.parseInt(pageSize));

        PagingResponse<ProductDTO> response = productService.findProductsByCategoryId(categoryId, request);

        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;

        return ResponseEntity.status(httpStatus).body(response);
    }
}

