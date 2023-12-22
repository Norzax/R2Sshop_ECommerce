package com.aclass.r2sshop_ecommerce.controllers;

import com.aclass.r2sshop_ecommerce.models.dto.AddressDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.address.AddressService;
import com.aclass.r2sshop_ecommerce.models.dto.common.AddressUpdateRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Address Controller" )
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/all_for_user")
    public ResponseEntity<ResponseDTO<List<AddressDTO>>> getAllAddressesForLoggedInUser() {
        ResponseDTO<List<AddressDTO>> response = addressService.getAllAddressesForLoggedInUser();
        HttpStatus httpStatus = response.getStatus().equals(String.valueOf(HttpStatus.NOT_FOUND.value())) ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PostMapping("/add_for_user")
    public ResponseEntity<ResponseDTO<AddressDTO>> addAddressForLoggedInUser(@Valid @RequestBody AddressDTO addressDTO) {
        ResponseDTO<AddressDTO> response = addressService.addAddressForLoggedInUser(addressDTO);
        HttpStatus httpStatus = response.getStatus().equals(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())) ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.CREATED;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PutMapping("/update_for_user/{addressId}")
    public ResponseEntity<ResponseDTO<AddressDTO>> updateAddressForLoggedInUser(@PathVariable Long addressId, @Valid @RequestBody AddressUpdateRequestDTO updateRequest) {
        ResponseDTO<AddressDTO> response = addressService.updateAddressForLoggedInUser(addressId, updateRequest);
        HttpStatus httpStatus = response.getStatus().equals(String.valueOf(HttpStatus.NOT_FOUND.value())) ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @DeleteMapping("/delete_for_user/{addressId}")
    public ResponseEntity<ResponseDTO<Void>> deleteAddress(@PathVariable Long addressId) {
        ResponseDTO<Void> response = addressService.deleteAddress(addressId);
        HttpStatus httpStatus = response.getStatus().equals(String.valueOf(HttpStatus.NOT_FOUND.value())) ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO<List<AddressDTO>>> findAll() {
        ResponseDTO<List<AddressDTO>> response = addressService.findAll();
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ResponseDTO<AddressDTO>> findById(@PathVariable Long id) {
        ResponseDTO<AddressDTO> response = addressService.findById(id);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<AddressDTO>> create(@Valid @RequestBody AddressDTO addressDTO) {
        ResponseDTO<AddressDTO> response = addressService.create(addressDTO);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO<AddressDTO>> update(@PathVariable Long id, @Valid @RequestBody AddressDTO updatedAddressDTO) {
        ResponseDTO<AddressDTO> response = addressService.update(id, updatedAddressDTO);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long id) {
        ResponseDTO<Void> response = addressService.delete(id);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }
}
