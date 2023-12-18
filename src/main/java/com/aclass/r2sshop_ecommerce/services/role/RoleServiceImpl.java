package com.aclass.r2sshop_ecommerce.services.role;

import com.aclass.r2sshop_ecommerce.constants.AppConstants;
import com.aclass.r2sshop_ecommerce.models.dto.RoleDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.repositories.RoleRepository;
import com.aclass.r2sshop_ecommerce.services.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Override
    public ResponseDTO<List<RoleDTO>> findAll() {
        return null;
    }

    @Override
    public ResponseDTO<RoleDTO> findById(Long id) {
        return null;
    }

    @Override
    public ResponseDTO<RoleDTO> create(RoleDTO dto) {
        return null;
    }

    @Override
    public ResponseDTO<RoleDTO> update(Long id, RoleDTO dto) {
        return null;
    }

    @Override
    public ResponseDTO<Void> delete(Long id) {
        return null;
    }
}
