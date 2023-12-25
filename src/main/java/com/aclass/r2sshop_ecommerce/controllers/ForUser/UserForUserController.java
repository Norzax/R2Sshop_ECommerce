package com.aclass.r2sshop_ecommerce.controllers.ForUser;

import com.aclass.r2sshop_ecommerce.models.dto.UserDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.UserUpdateRequestDTO;
import com.aclass.r2sshop_ecommerce.services.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "2. User: <User>")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/user/user")
public class UserForUserController {

    private final UserService userService;

    @Autowired
    public UserForUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get-information")
    public ResponseEntity<ResponseDTO<UserDTO>> getInformationCurrentUser() {
        ResponseDTO<UserDTO> responseDTO = userService.getInforCurrentUser();
        return ResponseEntity.status(Integer.parseInt(responseDTO.getStatus())).body(responseDTO);
    }
    @PutMapping("/update-information")
    public ResponseEntity<ResponseDTO<UserDTO>> updateUserInformation(@Valid @RequestBody UserUpdateRequestDTO updateUserDTO) {
        ResponseDTO<UserDTO> response = userService.updateUserInformation(updateUserDTO);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }
}
