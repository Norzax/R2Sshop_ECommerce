package com.aclass.r2sshop_ecommerce.services.category;

import com.aclass.r2sshop_ecommerce.models.dto.CategoryDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.Service;

import java.util.List;

public interface CategoryService extends Service {
    ResponseDTO<List<CategoryDTO>> findAll();
    ResponseDTO<CategoryDTO> findById(Long categoryId);
    ResponseDTO<CategoryDTO> create(CategoryDTO categoryDTO);
    ResponseDTO<CategoryDTO> update(Long id, CategoryDTO updatedCategoryDTO);
    ResponseDTO<Void> delete(Long id);


}
