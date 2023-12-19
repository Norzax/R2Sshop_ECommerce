package com.aclass.r2sshop_ecommerce.services;

import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IService<D> {

    ResponseDTO<List<D>> findAll();

    ResponseDTO<D> findById(Long id);

    ResponseDTO<D> create(D dto);

    ResponseDTO<D> update(Long id, D dto);

    ResponseDTO<Void> delete(Long id);
}
