package com.aclass.r2sshop_ecommerce.services.address;

import com.aclass.r2sshop_ecommerce.constants.AppConstants;
import com.aclass.r2sshop_ecommerce.models.dto.AddressDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.AddressUpdateRequestDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.entity.AddressEntity;
import com.aclass.r2sshop_ecommerce.models.entity.UserEntity;
import com.aclass.r2sshop_ecommerce.repositories.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    private UserEntity getLoggedInUser() {
        // Lấy thông tin người dùng đang đăng nhập từ SecurityContextHolder
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public ResponseDTO<List<AddressDTO>> findAddressesForLoggedInUser() {
        try {

            UserEntity loggedInUser = getLoggedInUser();

            List<AddressEntity> addresses = addressRepository.findByUser(loggedInUser);

            List<AddressDTO> addressDTOs = addresses.stream()
                    .map(addressEntity -> modelMapper.map(addressEntity, AddressDTO.class))
                    .collect(Collectors.toList());

            return ResponseDTO.<List<AddressDTO>>builder()
                    .status(String.valueOf(HttpStatus.OK.value()))
                    .message(AppConstants.FOUND_LIST_MESSAGE)
                    .data(addressDTOs)
                    .build();
        } catch (Exception e) {
            return ResponseDTO.<List<AddressDTO>>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Override
    public ResponseDTO<AddressDTO> createAddressForLoggedInUser(AddressDTO addressDTO) {
        try {
            UserEntity loggedInUser = getLoggedInUser();

            Optional<AddressEntity> existingAddress = addressRepository.findByAddressAndUser(addressDTO.getAddress(), loggedInUser);
            if (existingAddress.isPresent()) {
                return ResponseDTO.<AddressDTO>builder()
                        .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .message(AppConstants.UN_AUTHORIZED_MESAGE)
                        .build();
            }

            AddressEntity newAddressEntity = modelMapper.map(addressDTO, AddressEntity.class);
            newAddressEntity.setUser(loggedInUser);
            AddressEntity savedAddressEntity = addressRepository.save(newAddressEntity);

            AddressDTO savedAddressDTO = modelMapper.map(savedAddressEntity, AddressDTO.class);

            return ResponseDTO.<AddressDTO>builder()
                    .status(String.valueOf(HttpStatus.CREATED.value()))
                    .message(AppConstants.CREATE_SUCCESS_MESSAGE)
                    .data(savedAddressDTO)
                    .build();
        } catch (Exception e) {
            return ResponseDTO.<AddressDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.CREATE_FAILED_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<AddressDTO> updateAddressForLoggedInUser(Long addressId, AddressUpdateRequestDTO updateRequest) {
        try {
            UserEntity loggedInUser = getLoggedInUser();

            Optional<AddressEntity> optionalAddress = addressRepository.findById(addressId);
            if (optionalAddress.isPresent()) {
                AddressEntity address = optionalAddress.get();
                if (!address.getUser().equals(loggedInUser)) {
                    return ResponseDTO.<AddressDTO>builder()
                            .status(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                            .message(AppConstants.UN_AUTHORIZED_MESAGE)
                            .build();
                }

                address.setAddress(updateRequest.getNewAddress());

                AddressEntity updatedAddress = addressRepository.save(address);

                AddressDTO updatedAddressDTO = modelMapper.map(updatedAddress, AddressDTO.class);

                return ResponseDTO.<AddressDTO>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message(AppConstants.UPDATE_SUCCESS_MESSAGE)
                        .data(updatedAddressDTO)
                        .build();
            } else {
                return ResponseDTO.<AddressDTO>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message(AppConstants.NOT_FOUND_MESSAGE)
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<AddressDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.UPDATE_FAILED_MESSAGE)
                    .build();
        }
    }


    @Override
    public ResponseDTO<Void> deleteAddressForLoggedInUser(Long addressId) {
        try {
            UserEntity loggedInUser = getLoggedInUser();

            Optional<AddressEntity> optionalAddress = addressRepository.findById(addressId);
            if (optionalAddress.isPresent()) {
                AddressEntity address = optionalAddress.get();
                if (!address.getUser().equals(loggedInUser)) {
                    return ResponseDTO.<Void>builder()
                            .status(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                            .message(AppConstants.UN_AUTHORIZED_MESAGE)
                            .build();
                }

                addressRepository.deleteById(addressId);

                return ResponseDTO.<Void>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message(AppConstants.DELETE_SUCCESS_MESSAGE)
                        .build();
            } else {
                return ResponseDTO.<Void>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message(AppConstants.NOT_FOUND_MESSAGE)
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
    public ResponseDTO<List<AddressDTO>> findAll() {
        List<AddressEntity> list = addressRepository.findAll();

        if (list.isEmpty()) {
            return ResponseDTO.<List<AddressDTO>>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND))
                    .message(AppConstants.NOT_FOUND_LIST_MESSAGE)
                    .build();
        }

        List<AddressDTO> listRes = list.stream()
                .map(addressEntity -> modelMapper.map(addressEntity, AddressDTO.class))
                .collect(Collectors.toList());

        return ResponseDTO.<List<AddressDTO>>builder()
                .status(String.valueOf(HttpStatus.OK))
                .message(AppConstants.FOUND_LIST_MESSAGE)
                .data(listRes)
                .build();
    }

    @Override
    public ResponseDTO<AddressDTO> findById(Long id) {
        Optional<AddressEntity> optionalAddress = addressRepository.findById(id);

        if (optionalAddress.isPresent()) {
            AddressDTO addressDTO = modelMapper.map(optionalAddress.get(), AddressDTO.class);
            return ResponseDTO.<AddressDTO>builder()
                    .status(String.valueOf(HttpStatus.OK.value()))
                    .message(AppConstants.FOUND_MESSAGE)
                    .data(addressDTO)
                    .build();
        } else {
            return ResponseDTO.<AddressDTO>builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .message(AppConstants.NOT_FOUND_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<AddressDTO> create(AddressDTO addressDTO) {
        try {
            AddressEntity addressEntity = modelMapper.map(addressDTO, AddressEntity.class);
            AddressEntity savedAddress = addressRepository.save(addressEntity);
            AddressDTO savedAddressDTO = modelMapper.map(savedAddress, AddressDTO.class);

            return ResponseDTO.<AddressDTO>builder()
                    .status(String.valueOf(HttpStatus.CREATED.value()))
                    .message(AppConstants.CREATE_SUCCESS_MESSAGE)
                    .data(savedAddressDTO)
                    .build();
        }catch (Exception e) {
            return ResponseDTO.<AddressDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.CREATE_FAILED_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<AddressDTO> update(Long id, AddressDTO addressDTO) {
        try {
            Optional<AddressEntity> optionalAddress = addressRepository.findById(id);

            if (optionalAddress.isPresent()) {
                AddressEntity existingAddress = optionalAddress.get();

                existingAddress.setAddress(addressDTO.getAddress());

                AddressEntity updatedAddress = addressRepository.save(existingAddress);

                AddressDTO updatedAddressDTO = modelMapper.map(updatedAddress, AddressDTO.class);

                return ResponseDTO.<AddressDTO>builder()
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .message(AppConstants.UPDATE_SUCCESS_MESSAGE)
                        .data(updatedAddressDTO)
                        .build();
            } else {
                return ResponseDTO.<AddressDTO>builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .message(AppConstants.UPDATE_NOT_FOUND_MESSAGE)
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.<AddressDTO>builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(AppConstants.UPDATE_FAILED_MESSAGE)
                    .build();
        }
    }

    @Override
    public ResponseDTO<Void> delete(Long id) {
        try {
            Optional<AddressEntity> optionalAddress = addressRepository.findById(id);

            if (optionalAddress.isPresent()) {
                addressRepository.deleteById(id);

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
