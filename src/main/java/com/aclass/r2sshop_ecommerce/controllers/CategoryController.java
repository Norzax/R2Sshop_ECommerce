package com.aclass.r2sshop_ecommerce.controllers;

import com.aclass.r2sshop_ecommerce.models.dto.CategoryDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.category.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
