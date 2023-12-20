package com.aclass.r2sshop_ecommerce.repositories;

import com.aclass.r2sshop_ecommerce.models.entity.AddressEntity;
import com.aclass.r2sshop_ecommerce.models.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    AddressEntity findAddressEntityByUser_Id(Long userId);
}
