package com.aclass.r2sshop_ecommerce.services.user;

import com.aclass.r2sshop_ecommerce.Utilities.TokenUtil;
import com.aclass.r2sshop_ecommerce.constants.AppConstants;
import com.aclass.r2sshop_ecommerce.exceptions.UserException;
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
        return null;
    }

    @Override
    public ResponseDTO<UserDTO> findById(Long id) {
        return null;
    }

    @Override
    public ResponseDTO<UserDTO> update(Long id, UserDTO dto) {
        return null;
    }

    @Override
    public ResponseDTO<Void> delete(Long id) {
        return null;
    }
}
