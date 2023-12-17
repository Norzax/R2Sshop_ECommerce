package com.aclass.r2sshop_ecommerce.repositories;

import com.aclass.r2sshop_ecommerce.models.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT COUNT(p) FROM ProductEntity p WHERE p.category.id = :categoryId")
    int countProductsByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT p FROM ProductEntity p WHERE p.category.id = :categoryId")
    List<ProductEntity> findProductsByCategoryIdWithPaging(
            @Param("categoryId") Long categoryId,
            @Param("startIndex") int startIndex,
            @Param("pageSize") int pageSize
    );
}
