package com.aclass.r2sshop_ecommerce.controllers.ForAdmin;

import com.aclass.r2sshop_ecommerce.models.dto.ProductDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.PagingRequest;
import com.aclass.r2sshop_ecommerce.models.dto.common.PagingResponse;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.product.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "3. Admin: <Product>" )
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/admin/product")
public class ProductForAdminController {

    private final ProductService productService;

    @Autowired
    public ProductForAdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO<List<ProductDTO>>> findAll() {
        ResponseDTO<List<ProductDTO>> response = productService.findAll();
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ResponseDTO<ProductDTO>> findById(@PathVariable Long id) {
        ResponseDTO<ProductDTO> response = productService.findById(id);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<ProductDTO>> create(@Valid @RequestBody ProductDTO productDTO) {
        ResponseDTO<ProductDTO> response = productService.create(productDTO);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO<ProductDTO>> update(@PathVariable Long id, @Validated @RequestBody ProductDTO updatedProductDTO) {
        ResponseDTO<ProductDTO> response = productService.update(id, updatedProductDTO);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long id) {
        ResponseDTO<Void> response = productService.delete(id);
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

