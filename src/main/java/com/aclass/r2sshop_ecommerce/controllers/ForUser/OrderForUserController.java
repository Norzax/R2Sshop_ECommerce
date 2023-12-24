package com.aclass.r2sshop_ecommerce.controllers.ForUser;

import com.aclass.r2sshop_ecommerce.models.dto.common.OrderRequestDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.OrderResponseDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.order.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "2. User: <Order>" )
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/user/order")
public class OrderForUserController {

    private final OrderService orderService;

    @Autowired
    public OrderForUserController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping("/confirmOrder")
    public ResponseEntity<ResponseDTO<OrderResponseDTO>> Order(@RequestBody OrderRequestDTO requestDTO) {
        ResponseDTO<OrderResponseDTO> response = orderService.confirmOrder(requestDTO);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }
}