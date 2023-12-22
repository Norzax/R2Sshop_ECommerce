package com.aclass.r2sshop_ecommerce.services.user;

import com.aclass.r2sshop_ecommerce.models.dto.UserDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.*;
import com.aclass.r2sshop_ecommerce.services.IService;
import org.springframework.stereotype.Service;
@Service
public interface UserService extends IService<UserDTO> {
    ResponseDTO<LoginResponseDTO> login(LoginRequestDTO userDTO);
    ResponseDTO<RegisterResponseDTO> register(RegisterRequestDTO userDTO);
    ResponseDTO<RegisterResponseDTO> adminRegister(RegisterRequestDTO userDto);
    ResponseDTO<UserDTO> updateList(Long id, UserUpdateRequestDTO dto);
    ResponseDTO<UserDTO> getCurrentUser();
    ResponseDTO<UserDTO> updateUserInformation(UserUpdateRequestDTO userUpdateRequestDTO);
}
