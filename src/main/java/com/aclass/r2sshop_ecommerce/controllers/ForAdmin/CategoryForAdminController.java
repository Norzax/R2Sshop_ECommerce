package com.aclass.r2sshop_ecommerce.controllers.ForAdmin;

import com.aclass.r2sshop_ecommerce.models.dto.CategoryDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.category.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "3. Admin: <Category>" )
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/admin/category")
public class CategoryForAdminController {

    private final CategoryService categoryService;

    public CategoryForAdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO<List<CategoryDTO>>> findAll() {
        ResponseDTO<List<CategoryDTO>> response = categoryService.findAll();
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @GetMapping("/find-id/{id}")
    public ResponseEntity<ResponseDTO<CategoryDTO>> findById(@PathVariable Long id) {
        ResponseDTO<CategoryDTO> response = categoryService.findById(id);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<CategoryDTO>> create(@Valid @RequestBody CategoryDTO categoryDTO) {
        ResponseDTO<CategoryDTO> response = categoryService.create(categoryDTO);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO<CategoryDTO>> update(@PathVariable Long id, @Valid @RequestBody CategoryDTO updatedCategoryDTO) {
        ResponseDTO<CategoryDTO> response = categoryService.update(id, updatedCategoryDTO);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long id) {
        ResponseDTO<Void> response = categoryService.delete(id);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(httpStatus).body(response);
    }
}
