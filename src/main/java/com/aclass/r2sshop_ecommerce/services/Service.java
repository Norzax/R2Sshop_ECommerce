package com.aclass.r2sshop_ecommerce.services;

import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;

import java.util.List;

public interface Service<D> {

    ResponseDTO<List<D>> findAll();

    ResponseDTO<D> findById(Long id);

    ResponseDTO<D> create(D dto);

    ResponseDTO<D> update(Long id, D dto);

    ResponseDTO<Void> delete(Long id);
}
