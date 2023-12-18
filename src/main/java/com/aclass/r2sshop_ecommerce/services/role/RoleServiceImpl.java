package com.aclass.r2sshop_ecommerce.services.role;

import com.aclass.r2sshop_ecommerce.constants.AppConstants;
import com.aclass.r2sshop_ecommerce.models.dto.RoleDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.entity.RoleEntity;
import com.aclass.r2sshop_ecommerce.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseDTO<List<RoleDTO>> findAll() {
        return null;
    }

    @Override
    public ResponseDTO<RoleDTO> findById(Long id) {
        return null;
    }

    @Override
    public ResponseDTO<RoleDTO> create(RoleDTO roleDto) {
        RoleDTO newRoleDto = new RoleDTO();
        newRoleDto.setName(roleDto.getName());
        newRoleDto.setDescription(roleDto.getDescription());
        roleRepository.save(modelMapper.map(newRoleDto , RoleEntity.class));

        ResponseDTO<RoleDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setStatus(AppConstants.SUCCESS_STATUS);
        responseDTO.setData(newRoleDto);
        responseDTO.setMessage(AppConstants.SUCCESS_MESSAGE);

        return responseDTO;
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
