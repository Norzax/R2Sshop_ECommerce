package com.aclass.r2sshop_ecommerce.services.order;

import com.aclass.r2sshop_ecommerce.models.dto.CartDTO;
import com.aclass.r2sshop_ecommerce.models.dto.CartLineItemDTO;
import com.aclass.r2sshop_ecommerce.models.dto.OrderDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.entity.OrderEntity;
import com.aclass.r2sshop_ecommerce.repositories.OrderRepository;
import com.aclass.r2sshop_ecommerce.services.cart.CartService;
import com.aclass.r2sshop_ecommerce.services.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, CartService cartService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartService = cartService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseDTO<OrderDTO> createOrder() {
        try {
            // Lấy thông tin user đang login
            Long userId = userService.getCurrentUserId();

            // Lấy giỏ hàng của user
            ResponseDTO<CartDTO> userCartResponse = cartService.getUserCart(userId);

            // Kiểm tra xem giỏ hàng có sản phẩm không
            if (userCartResponse.getStatus().equals(String.valueOf(HttpStatus.OK.value()))) {
                CartDTO userCart = userCartResponse.getData();

                if (userCart.getCartLineItemEntities() == null || userCart.getCartLineItemEntities().isEmpty()) {
                    // Trả về thông báo hoặc xử lý khi giỏ hàng trống
                    // Ví dụ: throw new RuntimeException("Giỏ hàng trống");
                    return ResponseDTO.<OrderDTO>builder()
                            .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                            .message("Cart is empty")
                            .build();
                }

                // Tính toán tổng giá trị đơn hàng từ giỏ hàng
                Double totalPrice = userCart.getCartLineItemEntities().stream()
                        .mapToDouble(CartLineItemDTO::getTotalPrice)
                        .sum();

                // Tạo entity Order từ thông tin giỏ hàng
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setAddress(orderEntity.getAddress());
                orderEntity.setDeliveryTime(calculateDeliveryTime()); // Hàm tính toán thời gian giao hàng
                orderEntity.setTotalPrice(totalPrice);

                // Lưu đơn hàng vào cơ sở dữ liệu
                OrderEntity savedOrder = orderRepository.save(orderEntity);

                // Trả về thông tin đơn hàng đã tạo dưới dạng OrderDTO
                return ResponseDTO.<OrderDTO>builder()
                        .status(String.valueOf(HttpStatus.CREATED.value()))
                        .message("Order created successfully")
                        .data(modelMapper.map(savedOrder, OrderDTO.class))
                        .build();
            } else {
                // Trả về thông báo khi không thể lấy giỏ hàng
                return ResponseDTO.<OrderDTO>builder()
                        .status(userCartResponse.getStatus())
                        .message(userCartResponse.getMessage())
                        .build();
            }
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            return ResponseDTO.<OrderDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to create order")
                    .build();
        }
    }

    private Timestamp calculateDeliveryTime() {
        // Logic tính toán thời gian giao hàng (ví dụ: thêm 1 giờ cho thời gian hiện tại)
        return new Timestamp(System.currentTimeMillis() + 3600 * 1000);
    }

    @Override
    public ResponseDTO<List<OrderDTO>> findAll() {
        return null;
    }

    @Override
    public ResponseDTO<OrderDTO> findById(Long id) {
        return null;
    }

    @Override
    public ResponseDTO<OrderDTO> create(OrderDTO dto) {
        return null;
    }

    @Override
    public ResponseDTO<OrderDTO> update(Long id, OrderDTO dto) {
        return null;
    }

    @Override
    public ResponseDTO<Void> delete(Long id) {
        return null;
    }
}
