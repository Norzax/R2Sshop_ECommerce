package com.aclass.r2sshop_ecommerce.controllers.ForUser;

import com.aclass.r2sshop_ecommerce.models.dto.ProductDTO;
import com.aclass.r2sshop_ecommerce.models.dto.VariantProductDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.PagingRequest;
import com.aclass.r2sshop_ecommerce.models.dto.common.PagingResponse;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.product.ProductService;
import com.aclass.r2sshop_ecommerce.services.variant_product.VariantProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "2. User: <Variant Product>" )
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/user/variant_product")
public class VariantProductForUserController {
    private final VariantProductService variantProductService;

    public VariantProductForUserController(VariantProductService variantProductService) {
        this.variantProductService = variantProductService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO<List<VariantProductDTO>>> findAll() {
        ResponseDTO<List<VariantProductDTO>> response = variantProductService.findAll();
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<VariantProductDTO>> findById(@PathVariable Long id) {
        ResponseDTO<VariantProductDTO> response = variantProductService.findById(id);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ResponseDTO<List<VariantProductDTO>>> getVariantByProductId(@PathVariable Long productId) {
        ResponseDTO<List<VariantProductDTO>> response = variantProductService.getVariantProductsByProductId(productId);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }
}
