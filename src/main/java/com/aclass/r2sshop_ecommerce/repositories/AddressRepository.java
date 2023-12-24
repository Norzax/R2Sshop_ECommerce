package com.aclass.r2sshop_ecommerce.repositories;

import com.aclass.r2sshop_ecommerce.models.entity.AddressEntity;
import com.aclass.r2sshop_ecommerce.models.entity.UserEntity;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    AddressEntity findAddressEntityByUser_Id(Long userId);
    List<AddressEntity> findByUserId(Long userId);
    @Query("SELECT a FROM AddressEntity a WHERE a.address = :address AND a.user = :user")
    Optional<AddressEntity> findByAddressAndUser(@PathParam("address") String address, @PathParam("user") UserEntity user);
    @Query("select a from AddressEntity a where a.address = :address and a.user.id = :userId")
    Optional<AddressEntity> findAddressEntityByAddressAndUserId(@PathParam("address") String address, @PathParam(("userId")) Long userId);
}
