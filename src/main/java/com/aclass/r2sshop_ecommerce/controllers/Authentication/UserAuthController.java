package com.aclass.r2sshop_ecommerce.controllers.Authentication;

import com.aclass.r2sshop_ecommerce.models.dto.common.*;
import com.aclass.r2sshop_ecommerce.services.user.UserServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "1. Authentication: <User>")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserServiceImpl userServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<RegisterResponseDTO>> register(@RequestBody RegisterRequestDTO userDto) {
        return ResponseEntity.ok(userServiceImpl.register(userDto));
    }

    @PostMapping("/authentication")
    public ResponseEntity<ResponseDTO<LoginResponseDTO>> login(@RequestBody @Valid LoginRequestDTO userDto) {
        return ResponseEntity.ok(userServiceImpl.login(userDto));
    }
}

