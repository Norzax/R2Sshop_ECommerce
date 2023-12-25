package com.aclass.r2sshop_ecommerce.services.user;

import com.aclass.r2sshop_ecommerce.Utilities.TokenUtil;
import com.aclass.r2sshop_ecommerce.constants.AppConstants;
import com.aclass.r2sshop_ecommerce.models.dto.AddressDTO;
import com.aclass.r2sshop_ecommerce.models.dto.CartDTO;
import com.aclass.r2sshop_ecommerce.models.dto.CartLineItemDTO;
import com.aclass.r2sshop_ecommerce.models.dto.UserDTO;
import com.aclass.r2sshop_ecommerce.models.dto.UserDetail.CustomUserDetails;
import com.aclass.r2sshop_ecommerce.models.dto.common.*;
import com.aclass.r2sshop_ecommerce.models.dto.token.AccessTokenGenerated;
import com.aclass.r2sshop_ecommerce.models.entity.*;
import com.aclass.r2sshop_ecommerce.repositories.AddressRepository;
import com.aclass.r2sshop_ecommerce.repositories.CartRepository;
import com.aclass.r2sshop_ecommerce.repositories.RoleRepository;
import com.aclass.r2sshop_ecommerce.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenUtil tokenUtil;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, CartRepository cartRepository, AddressRepository addressRepository, AuthenticationManager authenticationManager, TokenUtil tokenUtil, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;
        this.authenticationManager = authenticationManager;
        this.tokenUtil = tokenUtil;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseDTO<LoginResponseDTO> login(LoginRequestDTO userDto) {
        try {
            var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));

            if (authentication.isAuthenticated()) {
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

                if (!userDetails.isEnabled()) {
                    return ResponseDTO.<LoginResponseDTO>builder()
                            .status(AppConstants.NOT_FOUND_STATUS)
                            .message(AppConstants.USER_INACTIVE_STATUS)
                            .build();
                }

                AccessTokenGenerated accessTokenGenerated = tokenUtil.generateToken(userDetails);
                return ResponseDTO.<LoginResponseDTO>builder()
                        .status("OK")
                        .message("Login success")
                        .data(
                                LoginResponseDTO.builder()
                                        .accessToken(accessTokenGenerated.getAccessToken())
                                        .expiredIn(accessTokenGenerated.getExpiredIn())
                                        .roles(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                                        .build()
                        ).build();
            } else {
                return ResponseDTO.<LoginResponseDTO>builder()
                        .status(AppConstants.NOT_FOUND_STATUS)
                        .message(AppConstants.USER_NOT_FOUND)
                        .build();
            }
        } catch (AuthenticationException e) {
            return ResponseDTO.<LoginResponseDTO>builder()
                    .status(AppConstants.NOT_FOUND_STATUS)
                    .message(AppConstants.INVALID_USERNAME_OR_PASSWORD)
                    .build();
        }
    }

    @Override
    public ResponseDTO<RegisterResponseDTO> register(RegisterRequestDTO userDto) {
        RoleEntity defaultRole = roleRepository.findByName("USER");
        return registerForAll(userDto, defaultRole);
    }

    @Override
    public ResponseDTO<UserDTO> create(UserDTO userDto) {
        Optional<UserEntity> existingUser = userRepository.findByUsername(userDto.getUsername());
        if(existingUser.isPresent()){
            return ResponseDTO.<UserDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                    .message(AppConstants.USERNAME_EXIST_MESSAGE)
                    .data(userDto)
                    .build();
        }

        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setUsername(userDto.getUsername());
        newUserEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUserEntity.setEmail(userDto.getEmail());
        newUserEntity.setFullName(userDto.getFullName());
        newUserEntity.setStatus(true);

        List<AddressEntity> addressList = new ArrayList<>();


        if (userDto.getAddressDTOList() != null) {
            addressList = userDto.getAddressDTOList()
                    .stream()
                    .map(addressDto -> modelMapper.map(addressDto, AddressEntity.class))
                    .collect(Collectors.toList());
        }

        newUserEntity.setAddressEntities(addressList);

        RoleEntity defaultRole = roleRepository.findByName("USER");
        if (defaultRole != null) {
            Set<RoleEntity> roles = new HashSet<>();
            roles.add(defaultRole);
            newUserEntity.setRoles(roles);
        } else {
            defaultRole = new RoleEntity();
            defaultRole.setName("USER");
            defaultRole.setDescription("Default role for regular users");
            roleRepository.save(defaultRole);

            Set<RoleEntity> roles = new HashSet<>();
            roles.add(defaultRole);
            newUserEntity.setRoles(roles);
        }

        UserEntity savedUser = userRepository.save(newUserEntity);
        UserDTO savedUserDto = modelMapper.map(savedUser, UserDTO.class);

        CartEntity newCart = new CartEntity();
        newCart.setUser(newUserEntity);

        Date createdDate = new Date();
        newCart.setCreateDate(createdDate);
        cartRepository.save(newCart);

        ResponseDTO<UserDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setStatus(AppConstants.SUCCESS_STATUS);
        responseDTO.setData(savedUserDto);
        responseDTO.setMessage(AppConstants.SUCCESS_MESSAGE);
        return responseDTO;
    }

    @Override
    public ResponseDTO<UserDTO> update(Long id, UserDTO dto) {
        return null;
    }

    @Override
    public ResponseDTO<List<UserDTO>> findAll() {
        List<UserEntity> list = userRepository.findAllByJoiningAddressEntity();

        if (list.isEmpty()) {
            return ResponseDTO.<List<UserDTO>>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND))
                    .message(AppConstants.NOT_FOUND_LIST_MESSAGE)
                    .build();
        }

        List<UserDTO> listRes = list.stream()
                .map(userEntity -> {
                    UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
                    List<AddressDTO> addressDTOList = userEntity.getAddressEntities().stream()
                            .map(addressEntity -> modelMapper.map(addressEntity, AddressDTO.class))
                            .collect(Collectors.toList());
                    userDTO.setAddressDTOList(addressDTOList);
                    return userDTO;
                })
                .collect(Collectors.toList());

        return ResponseDTO.<List<UserDTO>>builder()
                .status(String.valueOf(HttpStatus.OK))
                .message(AppConstants.FOUND_LIST_MESSAGE)
                .data(listRes)
                .build();
    }

    @Override
    public ResponseDTO<UserDTO> findById(Long id) {
        UserEntity userEntity = userRepository.findByIdJoiningAddressEntity(id);

        UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
        List<AddressDTO> addressDTOList = userEntity.getAddressEntities().stream()
                .map(addressEntity -> modelMapper.map(addressEntity, AddressDTO.class))
                .collect(Collectors.toList());
        userDTO.setAddressDTOList(addressDTOList);
        return ResponseDTO.<UserDTO>builder()
                .status(String.valueOf(HttpStatus.OK))
                .message(AppConstants.FOUND_LIST_MESSAGE)
                .data(userDTO)
                .build();
    }

    @Override
    public ResponseDTO<UserDTO> updateList(Long id, UserUpdateRequestDTO dto) {
        try {
            Optional<UserEntity> optionalUser = userRepository.findById(id);

            if (optionalUser.isPresent()) {
                UserEntity existingUser = optionalUser.get();
                if(dto.getFullName()!=null) {
                    existingUser.setFullName(dto.getFullName());
                }
                if(dto.getNewPassword() != null) {
                    if(!passwordEncoder.matches(dto.getOldPassword(), existingUser.getPassword())){
                        return ResponseDTO.<UserDTO>builder()
                                .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                                .message(AppConstants.USER_OLD_PASSWORD_NOT_MATCH)
                                .build();
                    }

                    if(passwordEncoder.matches(dto.getNewPassword(), existingUser.getPassword())){
                        return ResponseDTO.<UserDTO>builder()
                                .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                                .message(AppConstants.USER_SAME_PASSWORD)
                                .build();
                    }

                    if(!dto.getNewPassword().equals(dto.getConfirmNewPassword())){
                        return ResponseDTO.<UserDTO>builder()
                                .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                                .message(AppConstants.USER_UPDATE_PASSWORD_NOT_SAME)
                                .build();
                    }

                    existingUser.setPassword(passwordEncoder.encode(dto.getNewPassword()));
                }
                if(dto.getEmail() != null) {
                    existingUser.setEmail(dto.getEmail());
                }

                if (dto.getAddressesUpdate() != null) {
                    for (AddressUpdateRequestDTO address : dto.getAddressesUpdate()) {
                        Optional<AddressEntity> findAddress = addressRepository.findAddressEntityByAddressAndUserId(address.getOldAddress(),id);
                        if(findAddress.isPresent()){
                            Optional<AddressEntity> checkNewAddressExistence = addressRepository.findAddressEntityByAddressAndUserId(address.getNewAddress(),id);
                            if(checkNewAddressExistence.isPresent()){
                                return ResponseDTO.<UserDTO>builder()
                                        .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                                        .message(AppConstants.USER_ADDRESS_EXIST)
                                        .build();
                            }

                            AddressEntity updateAddress = findAddress.get();
                            updateAddress.setUser(existingUser);
                            updateAddress.setAddress(address.getNewAddress());
                            addressRepository.save(updateAddress);
                        }
                    }
                }

                UserEntity updatedUser = userRepository.save(existingUser);

                List<AddressEntity> currentUserAddress = addressRepository.findByUserId(id);

                UserDTO currentUserDTO = modelMapper.map(updatedUser, UserDTO.class);

                List<AddressDTO> addressDTOList = currentUserAddress.stream()
                        .map(addressEntity -> modelMapper.map(addressEntity, AddressDTO.class))
                        .collect(Collectors.toList());
                currentUserDTO.setAddressDTOList(addressDTOList);


                return ResponseDTO.<UserDTO>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message(AppConstants.UPDATE_SUCCESS_MESSAGE)
                        .data(currentUserDTO)
                        .build();
            } else {
                return ResponseDTO.<UserDTO>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message(AppConstants.UPDATE_NOT_FOUND_MESSAGE)
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<UserDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.UPDATE_FAILED_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<Void> delete(Long id) {
        try {
            Optional<UserEntity> optionalUser = userRepository.findById(id);

            if (optionalUser.isPresent()) {
                cartRepository.deleteById(cartRepository.findCartByUserId(id).getId());
                addressRepository.deleteById(addressRepository.findAddressEntityByUser_Id(id).getId());
                userRepository.deleteById(id);

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
    public ResponseDTO<RegisterResponseDTO> adminRegister(RegisterRequestDTO userDto) {
        RoleEntity defaultRole = roleRepository.findByName("ADMIN");
        return registerForAll(userDto, defaultRole);
    }

    private ResponseDTO<RegisterResponseDTO> registerForAll(RegisterRequestDTO userDto, RoleEntity defaultRole) {
        Optional<UserEntity> existingUser = userRepository.findByUsername(userDto.getUsername());

        if (existingUser.isPresent()) {
            return ResponseDTO.<RegisterResponseDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                    .message(AppConstants.USERNAME_EXIST_MESSAGE)
                    .data(null)
                    .build();
        }

        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setUsername(userDto.getUsername());
        newUserEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUserEntity.setEmail(userDto.getEmail());
        newUserEntity.setStatus(true);
        newUserEntity.setFullName(userDto.getFullName());

        if (defaultRole != null) {
            Set<RoleEntity> roles = new HashSet<>();
            roles.add(defaultRole);
            newUserEntity.setRoles(roles);
        } else {
            defaultRole = new RoleEntity();
            roleRepository.save(defaultRole);

            Set<RoleEntity> roles = new HashSet<>();
            roles.add(defaultRole);
            newUserEntity.setRoles(roles);
        }

        UserEntity userEntity = userRepository.save(newUserEntity);
        try {
            if(defaultRole.getName().equals("USER")){
                CartEntity newCart = new CartEntity();
                newCart.setUser(userEntity);
                newCart.setCreateDate(new Date());
                cartRepository.save(newCart);

                AddressEntity newAddress = new AddressEntity();
                newAddress.setAddress(userDto.getAddress());
                newAddress.setUser(userEntity);
                addressRepository.save(newAddress);
            }

            return getRegisterResponseDTOResponseDTO(userDto);
        } catch (Exception e) {
            ResponseDTO<RegisterResponseDTO> responseDTO = new ResponseDTO<>();
            responseDTO.setStatus(AppConstants.ERROR_STATUS);
            responseDTO.setMessage("Registration failed: " + e.getMessage());

            return responseDTO;
        }
    }

    private ResponseDTO<RegisterResponseDTO> getRegisterResponseDTOResponseDTO(RegisterRequestDTO userDto) {
        RegisterResponseDTO registerResponseDTO = new RegisterResponseDTO();
        registerResponseDTO.setAddress(userDto.getAddress());
        registerResponseDTO.setUsername(userDto.getUsername());
        registerResponseDTO.setFullName(userDto.getFullName());
        registerResponseDTO.setEmail(userDto.getEmail());

        ResponseDTO<RegisterResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setStatus(AppConstants.SUCCESS_STATUS);
        responseDTO.setData(registerResponseDTO);
        responseDTO.setMessage(AppConstants.SUCCESS_MESSAGE);
        return responseDTO;
    }

    @Override
    public Long getCurrentUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {

                return userDetails.getUser().getId();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ResponseDTO<UserDTO> getInforCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {

                UserEntity currentUserEntity = userDetails.getUser();

                List<AddressEntity> currentUserAddress = addressRepository.findByUserId(currentUserEntity.getId());

                UserDTO currentUserDTO = modelMapper.map(currentUserEntity, UserDTO.class);

                List<AddressDTO> addressDTOList = currentUserAddress.stream()
                        .map(addressEntity -> modelMapper.map(addressEntity, AddressDTO.class))
                        .collect(Collectors.toList());
                currentUserDTO.setAddressDTOList(addressDTOList);

                return ResponseDTO.<UserDTO>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message("Current user information retrieved successfully")
                        .data(currentUserDTO)
                        .build();
            } else {
                // Người dùng chưa đăng nhập
                return ResponseDTO.<UserDTO>builder()
                        .status(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                        .message("User not authenticated")
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<UserDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to retrieve current user information")
                    .build();
        }
    }

    @Override
    public ResponseDTO<UserDTO> updateUserInformation(UserUpdateRequestDTO dto) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            Optional<UserEntity> optionalUser = userRepository.findById(userDetails.getUser().getId());

            if (optionalUser.isPresent()) {
                UserEntity existingUser = optionalUser.get();
                if(dto.getFullName()!=null) {
                    existingUser.setFullName(dto.getFullName());
                }
                if(dto.getNewPassword() != null) {
                    if(!passwordEncoder.matches(dto.getOldPassword(), existingUser.getPassword())){
                        return ResponseDTO.<UserDTO>builder()
                                .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                                .message(AppConstants.USER_OLD_PASSWORD_NOT_MATCH)
                                .build();
                    }

                    if(passwordEncoder.matches(dto.getNewPassword(), existingUser.getPassword())){
                        return ResponseDTO.<UserDTO>builder()
                                .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                                .message(AppConstants.USER_SAME_PASSWORD)
                                .build();
                    }

                    if(!dto.getNewPassword().equals(dto.getConfirmNewPassword())){
                        return ResponseDTO.<UserDTO>builder()
                                .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                                .message(AppConstants.USER_UPDATE_PASSWORD_NOT_SAME)
                                .build();
                    }

                    existingUser.setPassword(passwordEncoder.encode(dto.getNewPassword()));
                }
                if(dto.getEmail() != null) {
                    existingUser.setEmail(dto.getEmail());
                }

                if (dto.getAddressesUpdate() != null) {
                    for (AddressUpdateRequestDTO address : dto.getAddressesUpdate()) {
                        Optional<AddressEntity> findAddress = addressRepository.findAddressEntityByAddressAndUserId(address.getOldAddress(),userDetails.getUser().getId());
                        if(findAddress.isPresent()){
                            Optional<AddressEntity> checkNewAddressExistence = addressRepository.findAddressEntityByAddressAndUserId(address.getNewAddress(),userDetails.getUser().getId());
                            if(checkNewAddressExistence.isPresent()){
                                return ResponseDTO.<UserDTO>builder()
                                        .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                                        .message(AppConstants.USER_ADDRESS_EXIST)
                                        .build();
                            }

                            AddressEntity updateAddress = findAddress.get();
                            updateAddress.setUser(existingUser);
                            updateAddress.setAddress(address.getNewAddress());
                            addressRepository.save(updateAddress);
                        }
                    }
                }

                UserEntity updatedUser = userRepository.save(existingUser);

                List<AddressEntity> currentUserAddress = addressRepository.findByUserId(userDetails.getUser().getId());

                UserDTO currentUserDTO = modelMapper.map(updatedUser, UserDTO.class);

                List<AddressDTO> addressDTOList = currentUserAddress.stream()
                        .map(addressEntity -> modelMapper.map(addressEntity, AddressDTO.class))
                        .collect(Collectors.toList());
                currentUserDTO.setAddressDTOList(addressDTOList);

                return ResponseDTO.<UserDTO>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message(AppConstants.UPDATE_SUCCESS_MESSAGE)
                        .data(currentUserDTO)
                        .build();
            } else {
                return ResponseDTO.<UserDTO>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message(AppConstants.UPDATE_NOT_FOUND_MESSAGE)
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<UserDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.UPDATE_FAILED_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<CartDTO> getUserCart(Long userId) {
        try {
            Optional<UserEntity> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                UserEntity userEntity = userOptional.get();

                CartEntity userCart = userEntity.getCart();

                if (userCart != null) {
                    CartDTO userCartDTO = modelMapper.map(userCart, CartDTO.class);

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
                    return ResponseDTO.<CartDTO>builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                            .message("User cart not found")
                            .build();
                }
            } else {
                return ResponseDTO.<CartDTO>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message("User not found")
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<CartDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to retrieve user cart")
                    .build();
        }
    }

    @Override
    public ResponseDTO<Long> getUserCartId(Long userId) {
        try {
            Optional<UserEntity> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                UserEntity user = optionalUser.get();
                CartEntity cart = user.getCart();

                if (cart != null) {
                    return ResponseDTO.<Long>builder()
                            .status(String.valueOf(HttpStatus.OK.value()))
                            .message("Cart ID found successfully")
                            .data(cart.getId())
                            .build();
                } else {
                    // Người dùng không có giỏ hàng
                    return ResponseDTO.<Long>builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                            .message("User does not have a cart.")
                            .build();
                }
            } else {
                // Người dùng không tồn tại
                return ResponseDTO.<Long>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message("User not found.")
                        .build();
            }
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            return ResponseDTO.<Long>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to get user cart ID")
                    .build();
        }
    }


    // trong truong hop cua project nay thi gio hang thi khong duoc cap nhat thong tin nhe!
    @Override
    public void updateUserCart(Long userId, CartDTO userCartDTO) {
        try {
            // Kiểm tra xem người dùng có tồn tại không
            Optional<UserEntity> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                UserEntity userEntity = userOptional.get();
                CartEntity userCart = userEntity.getCart();

                if (userCart != null) {
                    // Cập nhật thông tin giỏ hàng từ DTO
                    userCart.setCreateDate(userCartDTO.getCreateDate());

                    // Cập nhật danh sách các CartLineItemEntities từ DTO
                    List<CartLineItemEntity> updatedCartLineItems = userCartDTO.getCartLineItemEntities().stream()
                            .map(dto -> modelMapper.map(dto, CartLineItemEntity.class))
                            .collect(Collectors.toList());

                    // Gán danh sách mới vào giỏ hàng
                    userCart.setCartLineItemEntities(updatedCartLineItems);

                    // Lưu cập nhật vào cơ sở dữ liệu
                    userRepository.save(userEntity);

                    ResponseDTO.<Void>builder()
                            .status(String.valueOf(HttpStatus.OK.value()))
                            .message("User cart updated successfully")
                            .build();
                } else {
                    ResponseDTO.<Void>builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                            .message("User does not have a cart.")
                            .build();
                }
            } else {
                ResponseDTO.<Void>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message("User not found.")
                        .build();
            }
        } catch (Exception e) {
            ResponseDTO.<Void>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}


