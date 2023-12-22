package com.aclass.r2sshop_ecommerce.services.address;

import com.aclass.r2sshop_ecommerce.models.dto.AddressDTO;
import com.aclass.r2sshop_ecommerce.models.dto.UserDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.AddressUpdateRequestDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.UserUpdateRequestDTO;
import com.aclass.r2sshop_ecommerce.services.IService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService extends IService<AddressDTO> {

    ResponseDTO<List<AddressDTO>> getAllAddressesForLoggedInUser();
    ResponseDTO<AddressDTO> addAddressForLoggedInUser(AddressDTO addressDTO);
    ResponseDTO<AddressDTO> updateAddressForLoggedInUser(Long addressId, AddressUpdateRequestDTO updateRequest);
    ResponseDTO<Void> deleteAddress(Long addressId);

}
