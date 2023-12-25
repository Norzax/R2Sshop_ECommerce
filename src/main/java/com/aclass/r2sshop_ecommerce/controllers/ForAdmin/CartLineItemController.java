package com.aclass.r2sshop_ecommerce.controllers.ForAdmin;

import com.aclass.r2sshop_ecommerce.models.dto.CartLineItemDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.cart_line_item.CartLineItemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "3. Admin: <Cart Line Item>")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/cart-line-items")
public class CartLineItemController {

    private final CartLineItemService cartLineItemService;

    @Autowired
    public CartLineItemController(CartLineItemService cartLineItemService) {
        this.cartLineItemService = cartLineItemService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO<List<CartLineItemDTO>>> findAll() {
        ResponseDTO<List<CartLineItemDTO>> response = cartLineItemService.findAll();
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @GetMapping("/find-id/{id}")
    public ResponseEntity<ResponseDTO<CartLineItemDTO>> findById(@PathVariable Long id) {
        ResponseDTO<CartLineItemDTO> response = cartLineItemService.findById(id);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<CartLineItemDTO>> create(@Valid @RequestBody CartLineItemDTO cartLineItemDTO) {
        ResponseDTO<CartLineItemDTO> response = cartLineItemService.create(cartLineItemDTO);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO<CartLineItemDTO>> update(@PathVariable Long id, @Valid @RequestBody CartLineItemDTO updatedCartLineItemDTO) {
        ResponseDTO<CartLineItemDTO> response = cartLineItemService.update(id, updatedCartLineItemDTO);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long id) {
        ResponseDTO<Void> response = cartLineItemService.delete(id);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }
}
