package com.aclass.r2sshop_ecommerce.services.category;

import com.aclass.r2sshop_ecommerce.models.dto.CategoryDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.entity.CategoryEntity;
import com.aclass.r2sshop_ecommerce.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.aclass.r2sshop_ecommerce.constants.AppConstants;

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
                    .message(AppConstants.NOT_FOUND_LIST_MESSAGE)
                    .build();
        }

        List<CategoryDTO> listRes = list.stream()
                .map(categoryEntity -> modelMapper.map(categoryEntity, CategoryDTO.class))
                .collect(Collectors.toList());

        return ResponseDTO.<List<CategoryDTO>>builder()
                .status(String.valueOf(HttpStatus.OK))
                .message(AppConstants.FOUND_LIST_MESSAGE)
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
                    .message(AppConstants.FOUND_MESSAGE)
                    .data(categoryDTO)
                    .build();
        } else {
            return ResponseDTO.<CategoryDTO>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .message(AppConstants.NOT_FOUND_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<CategoryDTO> create(CategoryDTO categoryDTO) {
        try {
            CategoryEntity categoryEntity = modelMapper.map(categoryDTO, CategoryEntity.class);
            CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
            CategoryDTO savedCategoryDTO = modelMapper.map(savedCategory, CategoryDTO.class);

            return ResponseDTO.<CategoryDTO>builder()
                    .status(String.valueOf(HttpStatus.CREATED.value()))
                    .message(AppConstants.CREATE_SUCCESS_MESSAGE)
                    .data(savedCategoryDTO)
                    .build();
        } catch (Exception e) {
            return ResponseDTO.<CategoryDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.CREATE_FAILED_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<CategoryDTO> update(Long id, CategoryDTO categoryDTO) {
        try {
            Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);

            if (optionalCategory.isPresent()) {
                CategoryEntity existingCategory = optionalCategory.get();

                existingCategory.setName(categoryDTO.getName());
                existingCategory.setDescription(categoryDTO.getDescription());

                CategoryEntity updatedCategory = categoryRepository.save(existingCategory);
                CategoryDTO updatedCategoryDTO = modelMapper.map(updatedCategory, CategoryDTO.class);

                return ResponseDTO.<CategoryDTO>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message(AppConstants.UPDATE_SUCCESS_MESSAGE)
                        .data(updatedCategoryDTO)
                        .build();
            } else {
                return ResponseDTO.<CategoryDTO>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message(AppConstants.UPDATE_NOT_FOUND_MESSAGE)
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<CategoryDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.UPDATE_FAILED_MESSAGE)
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
                        .message(AppConstants.DELETE_SUCCESS_MESSAGE)
                        .build();
            } else {
                return ResponseDTO.<Void>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message(AppConstants.DELETE_NOT_FOUND_MESSAGE)
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<Void>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.DELETE_FAILED_MESSAGE)
                    .build();
        }
    }
}
