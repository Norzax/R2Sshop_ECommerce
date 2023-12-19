package com.aclass.r2sshop_ecommerce.services.user;

import com.aclass.r2sshop_ecommerce.Utilities.TokenUtil;
import com.aclass.r2sshop_ecommerce.constants.AppConstants;
import com.aclass.r2sshop_ecommerce.exceptions.UserException;
import com.aclass.r2sshop_ecommerce.models.dto.RoleDTO;
import com.aclass.r2sshop_ecommerce.models.dto.UserDTO;
import com.aclass.r2sshop_ecommerce.models.dto.UserDetail.CustomUserDetails;
import com.aclass.r2sshop_ecommerce.models.dto.common.*;
import com.aclass.r2sshop_ecommerce.models.dto.token.AccessTokenGenerated;
import com.aclass.r2sshop_ecommerce.models.entity.AddressEntity;
import com.aclass.r2sshop_ecommerce.models.entity.CartEntity;
import com.aclass.r2sshop_ecommerce.models.entity.RoleEntity;
import com.aclass.r2sshop_ecommerce.models.entity.UserEntity;
import com.aclass.r2sshop_ecommerce.repositories.AddressRepository;
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
    public ResponseDTO<LoginResponseDTO> login(LoginResquestDTO userDto) {
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
    public ResponseDTO<RegisterResponseDTO> register(RegisterRequestDTO userDto) {
        ResponseDTO<RegisterResponseDTO> responseDTO = new ResponseDTO<>();
        Optional<UserEntity> existingUser = userRepository.findByUsername(userDto.getUsername());

        if(existingUser.isPresent()){
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
        newUserEntity.setFullName(userDto.getFullName());

        RoleEntity defaultRole = roleRepository.findByName("USER");
        if (defaultRole != null) {
            Set<RoleEntity> roles = new HashSet<>();
            roles.add(defaultRole);
            newUserEntity.setRoles(roles);
        } else {
            if (defaultRole == null) {
                defaultRole = new RoleEntity();
                defaultRole.setName("USER");
                defaultRole.setDescription("Default role for regular users");
                roleRepository.save(defaultRole);

                Set<RoleEntity> roles = new HashSet<>();
                roles.add(defaultRole);
                newUserEntity.setRoles(roles);
            }
        }

        try {
            newUserEntity = userRepository.save(newUserEntity);

            CartEntity newCart = new CartEntity();
            newCart.setUser(newUserEntity);
            Date createdDate = new Date();
            newCart.setCreateDate(createdDate);
            cartRepository.save(newCart);

            AddressEntity newAddress = new AddressEntity();
            newAddress.setAddress(userDto.getAddress());
            newAddress.setUser(newUserEntity);
            addressRepository.save(newAddress);

            RegisterResponseDTO registerResponseDTO = new RegisterResponseDTO();
            registerResponseDTO.setAddress(userDto.getAddress());
            registerResponseDTO.setUsername(userDto.getUsername());
            registerResponseDTO.setFullName(userDto.getFullName());
            registerResponseDTO.setEmail(userDto.getEmail());

            responseDTO.setStatus(AppConstants.SUCCESS_STATUS);
            responseDTO.setData(registerResponseDTO);
            responseDTO.setMessage(AppConstants.SUCCESS_MESSAGE);
        } catch (Exception e) {
            responseDTO.setStatus(AppConstants.ERROR_STATUS);
            responseDTO.setMessage("Registration failed: " + e.getMessage());
        }

        return responseDTO;
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

        List<AddressEntity> addressEntities = new ArrayList<>();


        if (userDto.getAddressDTOList() != null) {
            addressEntities = userDto.getAddressDTOList()
                    .stream()
                    .map(addressDto -> modelMapper.map(addressDto, AddressEntity.class))
                    .collect(Collectors.toList());
        }
        newUserEntity.setAddressEntities(addressEntities);

        RoleEntity defaultRole = roleRepository.findByName("USER");
        if (defaultRole != null) {
            Set<RoleEntity> roles = new HashSet<>();
            roles.add(defaultRole);
            newUserEntity.setRoles(roles);
        } else {
            if (defaultRole == null) {
                defaultRole = new RoleEntity();
                defaultRole.setName("USER");
                defaultRole.setDescription("Default role for regular users");
                roleRepository.save(defaultRole);

                Set<RoleEntity> roles = new HashSet<>();
                roles.add(defaultRole);
                newUserEntity.setRoles(roles);
            }
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
    public ResponseDTO<List<UserDTO>> findAll() {
        List<UserEntity> list = userRepository.findAll();

        if (list.isEmpty()) {
            return ResponseDTO.<List<UserDTO>>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND))
                    .message(AppConstants.NOT_FOUND_LIST_MESSAGE)
                    .build();
        }

        List<UserDTO> listRes = list.stream()
                .map(userEntity -> modelMapper.map(userEntity, UserDTO.class))
                .collect(Collectors.toList());

        return ResponseDTO.<List<UserDTO>>builder()
                .status(String.valueOf(HttpStatus.OK))
                .message(AppConstants.FOUND_LIST_MESSAGE)
                .data(listRes)
                .build();
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
    public ResponseDTO<UserDTO> update(Long id, UserDTO dto) {
        try {
            Optional<UserEntity> optionalUser = userRepository.findById(id);

            if (optionalUser.isPresent()) {
                UserEntity existingUser = optionalUser.get();
                existingUser.setFullName(dto.getFullName());
                existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
                existingUser.setEmail(dto.getEmail());

                List<AddressEntity> addressEntities = new ArrayList<>();

                if (dto.getAddressDTOList() != null) {
                    addressEntities = dto.getAddressDTOList()
                            .stream()
                            .map(addressDto -> modelMapper.map(addressDto, AddressEntity.class))
                            .collect(Collectors.toList());
                }
                existingUser.setAddressEntities(addressEntities);

                UserEntity updatedUser = userRepository.save(existingUser);

                return ResponseDTO.<UserDTO>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message(AppConstants.UPDATE_SUCCESS_MESSAGE)
                        .data(modelMapper.map(updatedUser, UserDTO.class))
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
                cartRepository.deleteById(id);
                addressRepository.deleteById(id);
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


