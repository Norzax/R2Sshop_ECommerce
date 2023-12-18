package com.aclass.r2sshop_ecommerce.services.user;

import com.aclass.r2sshop_ecommerce.Utilities.TokenUtil;
import com.aclass.r2sshop_ecommerce.constants.AppConstants;
import com.aclass.r2sshop_ecommerce.constants.RoleEnum;
import com.aclass.r2sshop_ecommerce.exceptions.UserException;
import com.aclass.r2sshop_ecommerce.models.dto.CategoryDTO;
import com.aclass.r2sshop_ecommerce.models.dto.UserDTO;
import com.aclass.r2sshop_ecommerce.models.dto.UserDetail.CustomUserDetails;
import com.aclass.r2sshop_ecommerce.models.dto.common.LoginResponseDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.dto.token.AccessTokenGenerated;
import com.aclass.r2sshop_ecommerce.models.entity.AddressEntity;
import com.aclass.r2sshop_ecommerce.models.entity.CartEntity;
import com.aclass.r2sshop_ecommerce.models.entity.RoleEntity;
import com.aclass.r2sshop_ecommerce.models.entity.UserEntity;
import com.aclass.r2sshop_ecommerce.repositories.CartRepository;
import com.aclass.r2sshop_ecommerce.repositories.RoleRepository;
import com.aclass.r2sshop_ecommerce.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CartRepository cartRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenUtil tokenUtil;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, CartRepository cartRepository, AuthenticationManager authenticationManager, TokenUtil tokenUtil, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.cartRepository = cartRepository;
        this.authenticationManager = authenticationManager;
        this.tokenUtil = tokenUtil;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseDTO<LoginResponseDTO> login(UserDTO userDto) {
        try{
            var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
            if (authentication.isAuthenticated()){
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
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
                throw new UsernameNotFoundException("Not found username: " + userDto.getUsername());
            }
        } catch (AuthenticationException e ){
            throw new UserException(e.getMessage());
        }
    }

    @Override
    public ResponseDTO<UserDTO> create(UserDTO userDto) {
        try {
            // Create a new user entity and set its properties
            UserEntity newUserEntity = new UserEntity();
            newUserEntity.setUsername(userDto.getUsername());
            newUserEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
            newUserEntity.setEmail(userDto.getEmail());
            newUserEntity.setFullName(userDto.getFullName());

            // Map and set the address entities
            List<AddressEntity> addressEntities = new ArrayList<>();
            if (userDto.getAddressDTOList() != null) {
                addressEntities = userDto.getAddressDTOList()
                        .stream()
                        .map(addressDto -> modelMapper.map(addressDto, AddressEntity.class))
                        .collect(Collectors.toList());
            }
            newUserEntity.setAddressEntities(addressEntities);

            // Check if the default role exists, otherwise create and save it
            RoleEntity defaultRole = roleRepository.findByName(String.valueOf(RoleEnum.USER));
            if (defaultRole == null) {
                defaultRole = new RoleEntity();
                defaultRole.setName(String.valueOf(RoleEnum.USER));
                defaultRole.setDescription("Default role for regular users");
                roleRepository.save(defaultRole);
            }

            Set<RoleEntity> roles = new HashSet<>();
            roles.add(defaultRole);
            newUserEntity.setRoles(roles);

            UserEntity savedUser = userRepository.save(newUserEntity);
            UserDTO savedUserDto = modelMapper.map(savedUser, UserDTO.class);

            CartEntity newCart = new CartEntity();
            newCart.setUser(savedUser);
            newCart.setCreateDate(new Date());
            cartRepository.save(newCart);

            return ResponseDTO.<UserDTO>builder()
                    .status(String.valueOf(HttpStatus.CREATED.value()))
                    .message(AppConstants.CREATE_SUCCESS_MESSAGE)
                    .data(savedUserDto)
                    .build();
        } catch (Exception e) {
            return ResponseDTO.<UserDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.CREATE_FAILED_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<List<UserDTO>> findAll() {
        List<UserEntity> userList = userRepository.findAll();

        if (!userList.isEmpty()) {
            List<UserDTO> userDTOList = userList.stream()
                    .map(userEntity -> modelMapper.map(userEntity, UserDTO.class))
                    .collect(Collectors.toList());

            return ResponseDTO.<List<UserDTO>>builder()
                    .status(String.valueOf(HttpStatus.OK.value()))
                    .message(AppConstants.FOUND_LIST_MESSAGE)
                    .data(userDTOList)
                    .build();
        } else {
            return ResponseDTO.<List<UserDTO>>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .message(AppConstants.NOT_FOUND_LIST_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<UserDTO> findById(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            UserDTO userDTO = modelMapper.map(optionalUser.get(), UserDTO.class);
            return ResponseDTO.<UserDTO>builder()
                    .status(String.valueOf(HttpStatus.OK.value()))
                    .message(AppConstants.FOUND_MESSAGE)
                    .data(userDTO)
                    .build();
        } else {
            return ResponseDTO.<UserDTO>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .message(AppConstants.NOT_FOUND_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<UserDTO> update(Long id, UserDTO userDTO) {
        try {
            Optional<UserEntity> optionalUser = userRepository.findById(id);

            if (optionalUser.isPresent()) {
                UserEntity existingUser = optionalUser.get();

                existingUser.setUsername(userDTO.getUsername());
                existingUser.setEmail(userDTO.getEmail());
                existingUser.setFullName(userDTO.getFullName());
                // existingUser.setPassword(userDTO.getPassword());

                UserEntity updatedUser = userRepository.save(existingUser);
                UserDTO updatedUserDTO = modelMapper.map(updatedUser, UserDTO.class);

                return ResponseDTO.<UserDTO>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message(AppConstants.UPDATE_SUCCESS_MESSAGE)
                        .data(updatedUserDTO)
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
}
