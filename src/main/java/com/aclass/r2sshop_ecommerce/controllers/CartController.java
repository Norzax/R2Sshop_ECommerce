package com.aclass.r2sshop_ecommerce.controllers;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Cart Controller" )
@SecurityRequirement(name = "bearerAuth")
public class CartController {
}
