package com.aclass.r2sshop_ecommerce.controllers.Authentication;

import com.aclass.r2sshop_ecommerce.models.dto.common.*;
import com.aclass.r2sshop_ecommerce.services.user.UserServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "1. Authentication: <Admin>")
@RequestMapping("/api/v1/adminAuth")
@RequiredArgsConstructor
public class AdminAuthController {
    private final UserServiceImpl userServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<RegisterResponseDTO>> register(@RequestBody RegisterRequestDTO userDto) {
        return ResponseEntity.ok(userServiceImpl.adminRegister(userDto));
    }
}
