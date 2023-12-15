package com.aclass.r2sshop_ecommerce.services.category;

import com.aclass.r2sshop_ecommerce.models.dto.CategoryDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.entity.CategoryEntity;
import com.aclass.r2sshop_ecommerce.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseDTO<List<CategoryDTO>> findAll() {
        List<CategoryEntity> list = categoryRepository.findAll();

        if (list.isEmpty()) {
            return ResponseDTO.<List<CategoryDTO>>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND))
                    .message("Not found list categories")
                    .build();
        }

        List<CategoryDTO> listRes = list.stream()
                .map(categoryEntity -> modelMapper.map(categoryEntity, CategoryDTO.class))
                .collect(Collectors.toList());

        return ResponseDTO.<List<CategoryDTO>>builder()
                .status(String.valueOf(HttpStatus.OK))
                .message("Found list categories")
                .data(listRes)
                .build();
    }

    @Override
    public ResponseDTO<CategoryDTO> findById(Long id) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isPresent()) {
            CategoryDTO categoryDTO = modelMapper.map(optionalCategory.get(), CategoryDTO.class);
            return ResponseDTO.<CategoryDTO>builder()
                    .status(String.valueOf(HttpStatus.OK.value()))
                    .message("Category found")
                    .data(categoryDTO)
                    .build();
        } else {
            return ResponseDTO.<CategoryDTO>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .message("Category not found")
                    .build();
        }
    }

    @Override
    public ResponseDTO<CategoryDTO> create(CategoryDTO categoryDTO) {
        // Map the CategoryDTO to CategoryEntity
        CategoryEntity categoryEntity = modelMapper.map(categoryDTO, CategoryEntity.class);

        // Save the entity
        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);

        // Map the saved entity back to DTO
        CategoryDTO savedCategoryDTO = modelMapper.map(savedCategory, CategoryDTO.class);

        return ResponseDTO.<CategoryDTO>builder()
                .status(String.valueOf(HttpStatus.CREATED.value()))
                .message("Category created successfully")
                .data(savedCategoryDTO)
                .build();
    }

    @Override
    public ResponseDTO<CategoryDTO> update(Long id, CategoryDTO updatedCategoryDTO) {
        try {
            Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);

            if (optionalCategory.isPresent()) {
                CategoryEntity existingCategory = optionalCategory.get();

                // Update the fields with new values from updatedCategoryDTO
                existingCategory.setName(updatedCategoryDTO.getName());
                existingCategory.setDescription(updatedCategoryDTO.getDescription());

                // Save the updated category
                CategoryEntity updatedCategory = categoryRepository.save(existingCategory);

                CategoryDTO updatedCategoryDTO = modelMapper.map(updatedCategory, CategoryDTO.class);

                return ResponseDTO.<CategoryDTO>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message("Category updated successfully")
                        .data(updatedCategoryDTO)
                        .build();
            } else {
                return ResponseDTO.<CategoryDTO>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message("Category not found for update")
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<CategoryDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to update category")
                    .build();
        }
    }

    @Override
    public ResponseDTO<Void> delete(Long id) {
        try {
            Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);

            if (optionalCategory.isPresent()) {
                categoryRepository.deleteById(id);

                return ResponseDTO.<Void>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message("Category deleted successfully")
                        .build();
            } else {
                return ResponseDTO.<Void>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message("Category not found for deletion")
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<Void>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to delete category")
                    .build();
        }
    }



}
