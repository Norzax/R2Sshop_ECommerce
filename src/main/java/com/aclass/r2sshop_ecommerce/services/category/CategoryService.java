package com.aclass.r2sshop_ecommerce.services.category;

import com.aclass.r2sshop_ecommerce.models.dto.CategoryDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.IService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryIService extends IService<CategoryDTO> {
    ResponseDTO<List<CategoryDTO>> findAll();
    ResponseDTO<CategoryDTO> findById(Long id);
    ResponseDTO<CategoryDTO> create(CategoryDTO categoryDTO);
    ResponseDTO<CategoryDTO> update(Long id, CategoryDTO categoryDTO);
    ResponseDTO<Void> delete(Long id);

}
