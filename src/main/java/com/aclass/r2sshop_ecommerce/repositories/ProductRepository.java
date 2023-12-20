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

    @Query(nativeQuery = true,
            value = "select * from product p where p.category_id = :categoryId order by p.id asc limit :pageSize offset :startIndex"
    )
    List<ProductEntity> searchOrderByCategoryIdAsc(@Param("categoryId") Long categoryId, @Param("pageSize") int pageSize, @Param("startIndex") int startIndex);

    @Query(nativeQuery = true,
            value = "select * from product p where p.category_id = :categoryId order by p.id desc limit :pageSize offset :startIndex "
    )
    List<ProductEntity> searchOrderByCategoryIdDesc(@Param("categoryId") Long categoryId, @Param("pageSize") int pageSize, @Param("startIndex") int startIndex);

    @Query(nativeQuery = true, value = "select count(1) from product where category_id = :categoryId")
    int getTotalRecordSearch(Long categoryId);

    @Query("SELECT p FROM ProductEntity p LEFT JOIN FETCH p.variantProductEntities WHERE p.id = :productId")
    Optional<ProductEntity> findProductWithVariantsById(@Param("productId") Long productId);
}
