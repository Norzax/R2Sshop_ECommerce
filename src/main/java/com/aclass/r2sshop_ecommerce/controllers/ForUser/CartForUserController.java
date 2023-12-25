package com.aclass.r2sshop_ecommerce.controllers.ForUser;

import com.aclass.r2sshop_ecommerce.models.dto.CartDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.cart.CartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "2. User: <Cart>" )
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/user/cart")
public class CartForUserController {

    private final CartService cartService;

    @Autowired
    public CartForUserController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/find")
    public ResponseEntity<ResponseDTO<CartDTO>> findCartForCurrentUser() {
        ResponseDTO<CartDTO> response = cartService.findCartForCurrentUser();
        HttpStatus httpStatus = response.getStatus().equals(String.valueOf(HttpStatus.NOT_FOUND.value())) ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PostMapping("/add-product")
    public ResponseEntity<ResponseDTO<CartDTO>> addProductToCart(@RequestParam Long variantProductId,
                                                                 @RequestParam int quantity) {
        ResponseDTO<CartDTO> responseDTO = cartService.addProductToCart(variantProductId, quantity);
        return new ResponseEntity<>(responseDTO, HttpStatus.valueOf(Integer.parseInt(responseDTO.getStatus())));
    }
}
