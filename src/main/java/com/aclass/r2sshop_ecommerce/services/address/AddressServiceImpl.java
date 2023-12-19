package com.aclass.r2sshop_ecommerce.services.address;

import com.aclass.r2sshop_ecommerce.constants.AppConstants;
import com.aclass.r2sshop_ecommerce.models.dto.AddressDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.entity.AddressEntity;
import com.aclass.r2sshop_ecommerce.repositories.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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
