package com.aclass.r2sshop_ecommerce.repositories;

import com.aclass.r2sshop_ecommerce.models.dto.AddressDTO;
import com.aclass.r2sshop_ecommerce.models.entity.AddressEntity;
import com.aclass.r2sshop_ecommerce.models.entity.CartEntity;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    AddressEntity findAddressEntityByUser_Id(Long userId);
    @Query("select a from AddressEntity a where a.address = :address and a.user.id = :userId")
    Optional<AddressEntity> findAddressEntityByAddressAndUserId(@PathParam("address") String address, @PathParam(("userId")) Long userId);
}
