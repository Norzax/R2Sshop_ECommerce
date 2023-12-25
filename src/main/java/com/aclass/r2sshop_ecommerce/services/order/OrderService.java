package com.aclass.r2sshop_ecommerce.services.order;

import com.aclass.r2sshop_ecommerce.models.dto.OrderDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.OrderRequestDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.OrderResponseDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.IService;
import org.springframework.stereotype.Service;

@Service
public interface OrderService extends IService<OrderDTO> {
    ResponseDTO<OrderDTO> createOrder();
    ResponseDTO<OrderResponseDTO> confirmOrder(OrderRequestDTO requestDTO);
}
