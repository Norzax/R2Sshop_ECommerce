package com.aclass.r2sshop_ecommerce.repositories;

import com.aclass.r2sshop_ecommerce.models.dto.CartDTO;
import com.aclass.r2sshop_ecommerce.models.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    CartEntity findCartByUserId(Long userId);
}
