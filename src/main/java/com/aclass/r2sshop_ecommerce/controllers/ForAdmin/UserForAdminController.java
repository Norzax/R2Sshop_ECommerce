package com.aclass.r2sshop_ecommerce.controllers.ForAdmin;

import com.aclass.r2sshop_ecommerce.models.dto.UserDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.UserUpdateRequestDTO;
import com.aclass.r2sshop_ecommerce.services.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "3. Admin: <User>")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/admin/user")
public class UserForAdminController {

    private final UserService userService;

    public UserForAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO<List<UserDTO>>> findAll() {
        ResponseDTO<List<UserDTO>> response = userService.findAll();
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @GetMapping("/find-id/{id}")
    public ResponseEntity<ResponseDTO<UserDTO>> findById(@PathVariable Long id) {
        ResponseDTO<UserDTO> response = userService.findById(id);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<UserDTO>> createUser(@Valid @RequestBody UserDTO userDTO) {
        ResponseDTO<UserDTO> response = userService.create(userDTO);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO<UserDTO>> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequestDTO updatedUserDTO) {
        ResponseDTO<UserDTO> response = userService.updateList(id, updatedUserDTO);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteUser(@PathVariable Long id) {
        ResponseDTO<Void> response = userService.delete(id);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }
}
