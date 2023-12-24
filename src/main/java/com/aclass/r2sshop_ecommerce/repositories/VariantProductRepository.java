package com.aclass.r2sshop_ecommerce.repositories;

import com.aclass.r2sshop_ecommerce.models.entity.VariantProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariantProductRepository extends JpaRepository<VariantProductEntity, Long> {
    @Query("select v from VariantProductEntity v where v.product.id = :productId ")
    List<VariantProductEntity> findAllByProductId(Long productId);
}
