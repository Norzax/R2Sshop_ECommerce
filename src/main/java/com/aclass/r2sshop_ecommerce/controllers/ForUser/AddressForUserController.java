package com.aclass.r2sshop_ecommerce.controllers.ForUser;

import com.aclass.r2sshop_ecommerce.models.dto.AddressDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.address.AddressService;
import com.aclass.r2sshop_ecommerce.models.dto.common.AddressUpdateRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "2. User: <Address>" )
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/user/address")
public class AddressForUserController {

    private final AddressService addressService;

    @Autowired
    public AddressForUserController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/find")
    public ResponseEntity<ResponseDTO<List<AddressDTO>>> findAddressesForLoggedInUser() {
        ResponseDTO<List<AddressDTO>> response = addressService.findAddressesForLoggedInUser();
        HttpStatus httpStatus = response.getStatus().equals(String.valueOf(HttpStatus.NOT_FOUND.value())) ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<AddressDTO>> createAddressForLoggedInUser(@Valid @RequestBody AddressDTO addressDTO) {
        ResponseDTO<AddressDTO> response = addressService.createAddressForLoggedInUser(addressDTO);
        HttpStatus httpStatus = response.getStatus().equals(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())) ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.CREATED;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<AddressDTO>> updateAddressForLoggedInUser(@Valid @RequestBody AddressUpdateRequestDTO updateRequest) {
        ResponseDTO<AddressDTO> response = addressService.updateAddressForLoggedInUser(updateRequest);
        HttpStatus httpStatus = response.getStatus().equals(String.valueOf(HttpStatus.NOT_FOUND.value())) ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<ResponseDTO<Void>> deleteAddressForLoggedInUser(@PathVariable Long addressId) {
        ResponseDTO<Void> response = addressService.deleteAddressForLoggedInUser(addressId);
        HttpStatus httpStatus = response.getStatus().equals(String.valueOf(HttpStatus.NOT_FOUND.value())) ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

}
