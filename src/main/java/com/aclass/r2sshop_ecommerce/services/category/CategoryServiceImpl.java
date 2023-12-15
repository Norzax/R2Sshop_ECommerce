package com.aclass.r2sshop_ecommerce.services.category;

import com.aclass.r2sshop_ecommerce.models.dto.CategoryDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.entity.CategoryEntity;
import com.aclass.r2sshop_ecommerce.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
