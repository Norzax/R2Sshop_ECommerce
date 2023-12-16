package com.aclass.r2sshop_ecommerce.controllers;

import com.aclass.r2sshop_ecommerce.models.dto.CategoryDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDTO<List<CategoryDTO>>> findAll() {
        ResponseDTO<List<CategoryDTO>> response = categoryService.findAll();
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        return ResponseEntity.status(httpStatus).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<CategoryDTO>> findById(@PathVariable Long id) {
        ResponseDTO<CategoryDTO> response = categoryService.findById(id);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PostMapping("/")
    public ResponseEntity<ResponseDTO<CategoryDTO>> create(@Valid @RequestBody CategoryDTO categoryDTO) {
        ResponseDTO<CategoryDTO> response = categoryService.create(categoryDTO);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<CategoryDTO>> update(@PathVariable Long id, @Valid @RequestBody CategoryDTO updatedCategoryDTO) {
        ResponseDTO<CategoryDTO> response = categoryService.update(id, updatedCategoryDTO);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        return ResponseEntity.status(httpStatus).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long id) {
        ResponseDTO<Void> response = categoryService.delete(id);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        return ResponseEntity.status(httpStatus).body(response);
    }
}
