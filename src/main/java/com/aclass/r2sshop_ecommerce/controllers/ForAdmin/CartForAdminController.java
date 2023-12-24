package com.aclass.r2sshop_ecommerce.controllers.ForAdmin;


import com.aclass.r2sshop_ecommerce.models.dto.CartDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.cart.CartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "3. Admin: <Cart>" )
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/admin/cart")
public class CartForAdminController {
    private final CartService cartService;

    @Autowired
    public CartForAdminController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO<List<CartDTO>>> findAll() {
        ResponseDTO<List<CartDTO>> response = cartService.findAll();
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<CartDTO>> findById(@PathVariable Long id) {
        ResponseDTO<CartDTO> response = cartService.findById(id);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<CartDTO>> create(@Valid @RequestBody CartDTO cartDTO) {
        ResponseDTO<CartDTO> response = cartService.create(cartDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO<CartDTO>> update(@PathVariable Long id, @Valid @RequestBody CartDTO updateCartDTO) {
        ResponseDTO<CartDTO> response = cartService.update(id, updateCartDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long id) {
        ResponseDTO<Void> response = cartService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseDTO<CartDTO> findCartByUserId(@PathVariable Long userId) {
        return cartService.findCartByUserId(userId);
    }
}

