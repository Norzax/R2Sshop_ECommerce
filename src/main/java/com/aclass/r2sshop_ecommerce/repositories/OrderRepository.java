package com.aclass.r2sshop_ecommerce.repositories;

import com.aclass.r2sshop_ecommerce.models.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query("select cli.order from CartLineItemEntity cli " +
            "join cli.cart c " +
            "where c.user.id = :currentUserId " +
            "and cli.isDeleted = false")
    Optional<OrderEntity> getPresentOrder(Long currentUserId);
}