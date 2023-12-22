package com.aclass.r2sshop_ecommerce.services.cart;

import com.aclass.r2sshop_ecommerce.models.dto.CartDTO;
import com.aclass.r2sshop_ecommerce.models.dto.CartLineItemDTO;
import com.aclass.r2sshop_ecommerce.models.dto.VariantProductDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.entity.CartEntity;
import com.aclass.r2sshop_ecommerce.models.entity.UserEntity;
import com.aclass.r2sshop_ecommerce.repositories.CartRepository;
import com.aclass.r2sshop_ecommerce.repositories.UserRepository;
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
    private final UserService userService;
    private final VariantProductService variantProductService;
    private final ModelMapper modelMapper;

    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository, UserService userService, VariantProductService variantProductService, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.userService = userService;
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
            // Lấy ID của người dùng đang login
            Long userId = userService.getCurrentUserId();

            if (userId != null) {
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
    public ResponseDTO<CartDTO> addProductToCart(Long productId, int quantity) {
        try {
            Long userId = userService.getCurrentUserId();
            CartDTO userCartDTO = userService.getUserCart(userId).getData();
            Long cartId = userService.getUserCartId(userId).getData();

            ResponseDTO<List<VariantProductDTO>> variantProductDTO = variantProductService.getVariantProductsByProductId(productId);

            if (variantProductDTO != null && userCartDTO != null) {
                // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
                CartLineItemDTO existingCartItem = userCartDTO.getCartLineItemEntities().stream()
                        .filter(item -> item.getVariantProductId().equals(productId))
                        .findFirst()
                        .orElse(null);

                double productPrice = variantProductDTO.getData().get(0).getPrice();

                if (existingCartItem != null) {
                    // Nếu sản phẩm đã tồn tại, cập nhật số lượng
                    existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
                } else {
                    // Nếu sản phẩm chưa tồn tại, tạo mới CartLineItemDTO
                    CartLineItemDTO newCartItem = new CartLineItemDTO();
                    newCartItem.setCartId(cartId); //Id của Cart mà User đang login
                    newCartItem.setVariantProductId(productId);
                    newCartItem.setQuantity(quantity);
                    newCartItem.setTotalPrice(productPrice * quantity);
                    newCartItem.setAddedDate(new Timestamp(System.currentTimeMillis()));
                    newCartItem.setIsDeleted(false);

                    // Thêm CartLineItemDTO vào giỏ hàng
                    userCartDTO.getCartLineItemEntities().add(newCartItem);
                }

                // Cập nhật giỏ hàng trong cơ sở dữ liệu
                userService.updateUserCart(userId, userCartDTO);

                return ResponseDTO.<CartDTO>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message("Product added to cart successfully")
                        .data(userCartDTO)
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
