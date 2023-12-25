package com.aclass.r2sshop_ecommerce.controllers.ForAdmin;

import com.aclass.r2sshop_ecommerce.models.dto.VariantProductDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.variant_product.VariantProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "3. Admin: <Variant Product>" )
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/admin/variant")
public class VariantController {

    private final VariantProductService variantProductService;

    public VariantController(VariantProductService variantProductService) {
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

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<VariantProductDTO>> create(@Valid @RequestBody VariantProductDTO variantProductDTO) {
        ResponseDTO<VariantProductDTO> response = variantProductService.create(variantProductDTO);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO<VariantProductDTO>> update(@PathVariable Long id, @Valid @RequestBody VariantProductDTO updateVariantProductDTO) {
        ResponseDTO<VariantProductDTO> response = variantProductService.update(id, updateVariantProductDTO);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long id) {
        ResponseDTO<Void> response = variantProductService.delete(id);
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
