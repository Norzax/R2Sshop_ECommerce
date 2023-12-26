package com.aclass.r2sshop_ecommerce.services.order;

import com.aclass.r2sshop_ecommerce.configurations.PromoScheduler;
import com.aclass.r2sshop_ecommerce.constants.AppConstants;
import com.aclass.r2sshop_ecommerce.models.dto.CartDTO;
import com.aclass.r2sshop_ecommerce.models.dto.CartLineItemDTO;
import com.aclass.r2sshop_ecommerce.models.dto.OrderDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.OrderRequestDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.OrderResponseDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.entity.OrderEntity;
import com.aclass.r2sshop_ecommerce.models.entity.PromoEntity;
import com.aclass.r2sshop_ecommerce.models.entity.UserEntity;
import com.aclass.r2sshop_ecommerce.repositories.CartLineItemRepository;
import com.aclass.r2sshop_ecommerce.repositories.OrderRepository;
import com.aclass.r2sshop_ecommerce.repositories.PromoRepository;
import com.aclass.r2sshop_ecommerce.services.cart.CartService;
import com.aclass.r2sshop_ecommerce.services.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartLineItemRepository cartLineItemRepository;
    private final PromoRepository promoRepository;
    private final PromoScheduler promoScheduler;
    private final UserService userService;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CartLineItemRepository cartLineItemRepository, PromoRepository promoRepository, PromoScheduler promoScheduler, UserService userService, CartService cartService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.cartLineItemRepository = cartLineItemRepository;
        this.promoRepository = promoRepository;
        this.promoScheduler = promoScheduler;
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

    @Override
    public ResponseDTO<OrderResponseDTO> confirmOrder(OrderRequestDTO requestDTO) {
        try {
            int discount = 0;
            if(requestDTO.getPromoCode() != null && !requestDTO.getPromoCode().isEmpty()){
                Optional<PromoEntity> findPromo = promoRepository.getByCodeAndValid(requestDTO.getPromoCode());
                if(findPromo.isPresent()){
                    if(findPromo.get().isOrderDiscount()) {
                        discount = findPromo.get().getDiscountPercentage();
                        if (findPromo.get().getUsageLimit() > 0) {
                            findPromo.get().setUsageLimit(findPromo.get().getUsageLimit() - 1);
                            promoRepository.save(findPromo.get());
                        } else if (findPromo.get().getUsageLimit() == 0) {
                            promoScheduler.updateCartLineItemTotalPrices();
                        }
                    } else if(!findPromo.get().isOrderDiscount()){
                        return ResponseDTO.<OrderResponseDTO>builder()
                            .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                            .message("Code not exists")
                            .build();
                    }
                }
            }

            Long currentUserId = userService.getCurrentUserId();
            Optional<OrderEntity> currentOrder = orderRepository.getPresentOrder(currentUserId);
            if (currentOrder.isPresent()) {
                OrderResponseDTO completeOrder = new OrderResponseDTO();
                completeOrder.setId(currentOrder.get().getId());
                completeOrder.setAddress(requestDTO.getAddress());
                double totalPrice = cartLineItemRepository.getTotalPrice(completeOrder.getId());
                completeOrder.setTotalPrice(totalPrice);
                completeOrder.setDiscountPrice(totalPrice - (totalPrice * discount / 100));
                completeOrder.setUserInfo(requestDTO.getUserInfo());
                completeOrder.setDeliveryTime(calculateDeliveryTime());

                OrderEntity saved = new OrderEntity();
                saved.setId(currentOrder.get().getId());
                saved.setAddress(requestDTO.getAddress());
                saved.setTotalPrice(totalPrice - (totalPrice * discount / 100));
                saved.setUser(modelMapper.map(userService.getInforCurrentUser().getData(), UserEntity.class));
                saved.setDeliveryTime(calculateDeliveryTime());
                if(requestDTO.getUserInfo() == null || requestDTO.getUserInfo().trim().isEmpty()) {
                    saved.setReceiver(userService.getInforCurrentUser().getData().getFullName());
                } else {
                    saved.setReceiver(requestDTO.getUserInfo());
                }
                orderRepository.save(saved);

                cartLineItemRepository.softDelete(currentOrder.get().getId());

                return ResponseDTO.<OrderResponseDTO>builder()
                        .status(AppConstants.SUCCESS_STATUS)
                        .message("Your order completed")
                        .data(completeOrder)
                        .build();
            } else {
                return ResponseDTO.<OrderResponseDTO>builder()
                        .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .message("Currently you dont have any order")
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<OrderResponseDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Can not finish your order")
                    .build();
        }
    }

    private Timestamp calculateDeliveryTime() {
        // Logic tính toán thời gian giao hàng (ví dụ: thêm 1 ngày cho thời gian hiện tại)
        return new Timestamp(System.currentTimeMillis() + 24 * 3600 * 1000);
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
