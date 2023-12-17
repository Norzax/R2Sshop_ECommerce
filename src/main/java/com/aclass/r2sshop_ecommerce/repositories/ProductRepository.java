package com.aclass.r2sshop_ecommerce.repositories;

import com.aclass.r2sshop_ecommerce.models.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT COUNT(p) FROM ProductEntity p WHERE p.category.id = :categoryId")
    long countProductsByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT p FROM ProductEntity p WHERE p.category.id = ?1")
    List<ProductEntity> findProductsByCategoryIdWithPaging(Long categoryId, int startIndex, int pageSize);

    @Query("SELECT p FROM ProductEntity p LEFT JOIN FETCH p.variantProductEntities WHERE p.id = :productId")
    Optional<ProductEntity> findProductWithVariantsById(@Param("productId") Long productId);
}
