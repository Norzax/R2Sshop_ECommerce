package com.aclass.r2sshop_ecommerce.repositories;

import com.aclass.r2sshop_ecommerce.models.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
}
