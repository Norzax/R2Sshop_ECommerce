package com.aclass.r2sshop_ecommerce.services.cart;

import com.aclass.r2sshop_ecommerce.configurations.PromoScheduler;
import com.aclass.r2sshop_ecommerce.models.dto.CartDTO;
import com.aclass.r2sshop_ecommerce.models.dto.CartLineItemDTO;
import com.aclass.r2sshop_ecommerce.models.dto.VariantProductDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.entity.CartEntity;
import com.aclass.r2sshop_ecommerce.models.entity.CartLineItemEntity;
import com.aclass.r2sshop_ecommerce.models.entity.OrderEntity;
import com.aclass.r2sshop_ecommerce.models.entity.UserEntity;
import com.aclass.r2sshop_ecommerce.repositories.*;
import com.aclass.r2sshop_ecommerce.services.user.UserService;
import com.aclass.r2sshop_ecommerce.services.variant_product.VariantProductService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.aclass.r2sshop_ecommerce.constants.AppConstants;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartLineItemRepository cartLineItemRepository;
    private final VariantProductRepository variantProductRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final PromoScheduler promoScheduler;
    private final VariantProductService variantProductService;
    private final ModelMapper modelMapper;

    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository, CartLineItemRepository cartLineItemRepository, VariantProductRepository variantProductRepository, OrderRepository orderRepository, UserService userService, PromoScheduler promoScheduler, VariantProductService variantProductService, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.cartLineItemRepository = cartLineItemRepository;
        this.variantProductRepository = variantProductRepository;
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.promoScheduler = promoScheduler;
        this.variantProductService = variantProductService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseDTO<List<CartDTO>> findAll() {
        List<CartEntity> list = cartRepository.findAll();

        if (list.isEmpty()) {
            return ResponseDTO.<List<CartDTO>>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND))
                    .message(AppConstants.NOT_FOUND_LIST_MESSAGE)
                    .build();
        }

        List<CartDTO> listRes = list.stream()
                .map(cartEntity -> modelMapper.map(cartEntity, CartDTO.class))
                .collect(Collectors.toList());

        return ResponseDTO.<List<CartDTO>>builder()
                .status(String.valueOf(HttpStatus.OK))
                .message(AppConstants.FOUND_LIST_MESSAGE)
                .data(listRes)
                .build();
    }

    @Override
    public ResponseDTO<CartDTO> findById(Long id) {
        Optional<CartEntity> optionalCart = cartRepository.findById(id);

        if (optionalCart.isPresent()) {
            CartDTO cartDTO = modelMapper.map(optionalCart.get(), CartDTO.class);
            return ResponseDTO.<CartDTO>builder()
                    .status(String.valueOf(HttpStatus.OK.value()))
                    .message(AppConstants.FOUND_MESSAGE)
                    .data(cartDTO)
                    .build();
        } else {
            return ResponseDTO.<CartDTO>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .message(AppConstants.NOT_FOUND_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<CartDTO> create(CartDTO cartDTO) {
        try {
            CartEntity cartEntity = modelMapper.map(cartDTO, CartEntity.class);
            CartEntity savedCart = cartRepository.save(cartEntity);
            CartDTO savedCartDTO = modelMapper.map(savedCart, CartDTO.class);

            return ResponseDTO.<CartDTO>builder()
                    .status(String.valueOf(HttpStatus.CREATED.value()))
                    .message(AppConstants.CREATE_SUCCESS_MESSAGE)
                    .data(savedCartDTO)
                    .build();
        } catch (Exception e) {
            return ResponseDTO.<CartDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.CREATE_FAILED_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<CartDTO> update(Long id, CartDTO cartDTO) {
        try {
            Optional<CartEntity> optionalCart = cartRepository.findById(id);

            if (optionalCart.isPresent()) {
                CartEntity existingCart = optionalCart.get();

                // Cập nhật các trường cần thiết tại đây

                CartEntity updatedCart = cartRepository.save(existingCart);
                CartDTO updatedCartDTO = modelMapper.map(updatedCart, CartDTO.class);

                return ResponseDTO.<CartDTO>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message(AppConstants.UPDATE_SUCCESS_MESSAGE)
                        .data(updatedCartDTO)
                        .build();
            } else {
                return ResponseDTO.<CartDTO>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message(AppConstants.UPDATE_NOT_FOUND_MESSAGE)
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<CartDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.UPDATE_FAILED_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<Void> delete(Long id) {
        try {
            Optional<CartEntity> optionalCart = cartRepository.findById(id);

            if (optionalCart.isPresent()) {
                cartRepository.deleteById(id);

                return ResponseDTO.<Void>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message(AppConstants.DELETE_SUCCESS_MESSAGE)
                        .build();
            } else {
                return ResponseDTO.<Void>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message(AppConstants.DELETE_NOT_FOUND_MESSAGE)
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<Void>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.DELETE_FAILED_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<CartDTO> findCartByUserId(Long userId) {
        try {
            Optional<UserEntity> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                UserEntity user = optionalUser.get();
                CartEntity cart = user.getCart();

                if (cart != null) {
                    CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);


                    return ResponseDTO.<CartDTO>builder()
                            .status(String.valueOf(HttpStatus.OK.value()))
                            .message(AppConstants.FOUND_MESSAGE)
                            .data(cartDTO)
                            .build();
                } else {
                    return ResponseDTO.<CartDTO>builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                            .message("User does not have a cart.")
                            .build();
                }
            } else {
                return ResponseDTO.<CartDTO>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message("User not found.")
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<CartDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Override
    public ResponseDTO<CartDTO> getUserCart(Long userId) {
        try {
            // Kiểm tra xem người dùng có tồn tại không
            Optional<UserEntity> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                UserEntity userEntity = userOptional.get();

                // Lấy giỏ hàng của người dùng
                CartEntity userCart = userEntity.getCart();

                if (userCart != null) {
                    // Map giỏ hàng sang DTO
                    CartDTO userCartDTO = modelMapper.map(userCart, CartDTO.class);

                    // Map các CartLineItem sang DTO
                    List<CartLineItemDTO> cartLineItemsDTO = userCart.getCartLineItemEntities().stream()
                            .map(cartLineItemEntity -> modelMapper.map(cartLineItemEntity, CartLineItemDTO.class))
                            .collect(Collectors.toList());

                    userCartDTO.setCartLineItemEntities(cartLineItemsDTO);

                    return ResponseDTO.<CartDTO>builder()
                            .status(String.valueOf(HttpStatus.OK.value()))
                            .message("User cart retrieved successfully")
                            .data(userCartDTO)
                            .build();
                } else {
                    // Nếu giỏ hàng không tồn tại
                    return ResponseDTO.<CartDTO>builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                            .message("User cart not found")
                            .build();
                }
            } else {
                // Nếu người dùng không tồn tại
                return ResponseDTO.<CartDTO>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message("User not found")
                        .build();
            }
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            return ResponseDTO.<CartDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to retrieve user cart")
                    .build();
        }
    }

    @Override
    public ResponseDTO<CartDTO> findCartForCurrentUser() {
        try {
            Long userId = userService.getCurrentUserId();

            if (userId != null) {
                Optional<UserEntity> optionalUser = userRepository.findById(userId);

                if (optionalUser.isPresent()) {
                    List<CartLineItemEntity> cartLineItems = cartLineItemRepository.findByCart_User_Id(userId);

                    if (!cartLineItems.isEmpty()) {
                        List<CartLineItemDTO> cartLineItemDTOs = cartLineItems.stream()
                                .map(cartLineItem -> {
                                    CartLineItemDTO cartLineItemDTO = modelMapper.map(cartLineItem, CartLineItemDTO.class);
                                    VariantProductDTO variantProductDTO = modelMapper.map(cartLineItem.getVariantProduct(), VariantProductDTO.class);
                                    cartLineItemDTO.setVariantProductDTO(variantProductDTO);
                                    return cartLineItemDTO;
                                })
                                .collect(Collectors.toList());

                        CartDTO cartDTO = new CartDTO();
                        cartDTO.setCartLineItemEntities(cartLineItemDTOs);

                        return ResponseDTO.<CartDTO>builder()
                                .status(String.valueOf(HttpStatus.OK.value()))
                                .message(AppConstants.FOUND_MESSAGE)
                                .data(cartDTO)
                                .build();
                    } else {
                        return ResponseDTO.<CartDTO>builder()
                                .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                                .message("User does not have cart items.")
                                .build();
                    }
                } else {
                    return ResponseDTO.<CartDTO>builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                            .message("User not found.")
                            .build();
                }
            } else {
                return ResponseDTO.<CartDTO>builder()
                        .status(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                        .message("User not authenticated.")
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<CartDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Override
    public ResponseDTO<CartDTO> addProductToCart(Long variantProductId, int quantity) {
        try {
            Long userId = userService.getCurrentUserId();

            CartDTO userCartDTO = modelMapper.map(cartRepository.findCartByUserId(userId), CartDTO.class);
            Long cartId = userCartDTO.getId();

            ResponseDTO<VariantProductDTO> variantProductDTO = variantProductService.findById(variantProductId);

            if (variantProductDTO.getData() != null && userCartDTO != null) {
                Optional<CartLineItemEntity> existingCartItem = cartLineItemRepository.getExistCartLineItem(variantProductId, cartId);

                if (quantity == 0) {
                    // Nếu quantity = 0, xóa tất cả CartLineItem của sản phẩm này
                    cartLineItemRepository.deleteAllByVariantProduct_IdAndCart_Id(variantProductId, cartId);
                } else if (existingCartItem.isPresent() && !existingCartItem.get().getIsDeleted()) {
                    existingCartItem.get().setQuantity(existingCartItem.get().getQuantity() + quantity);

                    if (existingCartItem.get().getQuantity() <= 0) {
                        cartLineItemRepository.deleteById(existingCartItem.get().getId());
                        orderRepository.deleteById(existingCartItem.get().getOrder().getId());
                    } else {
                        double newTotalPrice = existingCartItem.get().getQuantity() * variantProductDTO.getData().getPrice();
                        existingCartItem.get().setTotalPrice(newTotalPrice);
                        cartLineItemRepository.save(existingCartItem.get());
                    }
                } else {
                    Long orderId = cartLineItemRepository.getExistOrderIdByCarId(cartId);

                    if (orderId != null) {
                        VariantProductDTO getInfo = modelMapper.map(variantProductRepository.findById(variantProductId).get(), VariantProductDTO.class);
                        CartLineItemDTO newCartItem = new CartLineItemDTO();
                        newCartItem.setCartId(cartId);
                        newCartItem.setVariantProductDTO(getInfo);
                        newCartItem.setQuantity(quantity);
                        double totalPrice = variantProductDTO.getData().getPrice() * newCartItem.getQuantity();
                        newCartItem.setTotalPrice(totalPrice);
                        newCartItem.setOrderId(orderId);
                        newCartItem.setAddedDate(new Timestamp(System.currentTimeMillis()));
                        newCartItem.setIsDeleted(false);

                        userCartDTO.getCartLineItemEntities().add(newCartItem);
                        cartLineItemRepository.save(modelMapper.map(newCartItem, CartLineItemEntity.class));
                    } else {
                        OrderEntity newOrder = new OrderEntity();
                        OrderEntity getNewOrder = orderRepository.save(newOrder);

                        VariantProductDTO getInfo = modelMapper.map(variantProductRepository.findById(variantProductId).get(), VariantProductDTO.class);
                        CartLineItemDTO newCartItem = new CartLineItemDTO();
                        newCartItem.setCartId(cartId);
                        newCartItem.setVariantProductDTO(getInfo);
                        newCartItem.setQuantity(quantity);
                        double totalPrice = variantProductDTO.getData().getPrice() * newCartItem.getQuantity();
                        newCartItem.setTotalPrice(totalPrice);
                        newCartItem.setOrderId(getNewOrder.getId());
                        newCartItem.setAddedDate(new Timestamp(System.currentTimeMillis()));
                        newCartItem.setIsDeleted(false);

                        userCartDTO.getCartLineItemEntities().add(newCartItem);
                        cartLineItemRepository.save(modelMapper.map(newCartItem, CartLineItemEntity.class));
                    }
                }

                userService.updateUserCart(userId, userCartDTO);

                List<CartLineItemEntity> cartLineItems = cartLineItemRepository.findByCart_User_Id(userId);
                List<CartLineItemDTO> cartLineItemDTOs = cartLineItems.stream()
                        .map(cartLineItem -> {
                            CartLineItemDTO cartLineItemDTO = modelMapper.map(cartLineItem, CartLineItemDTO.class);
                            VariantProductDTO variantProductDTOs = modelMapper.map(cartLineItem.getVariantProduct(), VariantProductDTO.class);
                            cartLineItemDTO.setVariantProductDTO(variantProductDTOs);
                            return cartLineItemDTO;
                        })
                        .collect(Collectors.toList());

                CartDTO cartDTO = new CartDTO();
                cartDTO.setCartLineItemEntities(cartLineItemDTOs);

                promoScheduler.updateCartLineItemTotalPrices();

                return ResponseDTO.<CartDTO>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message("Product added to cart successfully")
                        .data(cartDTO)
                        .build();
            } else {
                return ResponseDTO.<CartDTO>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message("Product or user cart not found")
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<CartDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to add product to cart")
                    .build();
        }
    }
}
