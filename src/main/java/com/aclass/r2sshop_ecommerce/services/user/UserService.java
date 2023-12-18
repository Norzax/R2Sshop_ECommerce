package com.aclass.r2sshop_ecommerce.services.user;

import com.aclass.r2sshop_ecommerce.models.dto.UserDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.LoginResponseDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.entity.UserEntity;
import com.aclass.r2sshop_ecommerce.services.IService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends IService<UserDTO> {
    ResponseDTO<LoginResponseDTO> login(UserDTO userDTO);
}
