package com.aclass.r2sshop_ecommerce.services.address;

import com.aclass.r2sshop_ecommerce.models.dto.AddressDTO;
import com.aclass.r2sshop_ecommerce.models.dto.UserDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.AddressUpdateRequestDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.UserUpdateRequestDTO;
import com.aclass.r2sshop_ecommerce.services.IService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AddressService extends IService<AddressDTO> {

    ResponseDTO<List<AddressDTO>> findAddressesForLoggedInUser();
    ResponseDTO<AddressDTO> createAddressForLoggedInUser(AddressDTO addressDTO);
    ResponseDTO<AddressDTO> updateAddressForLoggedInUser(AddressUpdateRequestDTO updateRequest);
    ResponseDTO<Void> deleteAddressForLoggedInUser(Long addressId);
}
