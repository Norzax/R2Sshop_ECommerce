package com.aclass.r2sshop_ecommerce.repositories;

import com.aclass.r2sshop_ecommerce.models.entity.PromoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PromoRepository extends JpaRepository<PromoEntity, Long> {
    boolean existsByVariantProductsIdAndIsEnableTrue(Long variantProductId);
    @Modifying
    @Transactional
    @Query("update PromoEntity p set p.isEnable = false where p.endDate < current_timestamp and p.isEnable = true")
    void updateExpiredPromos();
    boolean existsByCode(String code);

    @Query("select p from PromoEntity  p where p.code = :code and p.isEnable = true and p.usageLimit > 0 and p.isOrderDiscount = true")
    Optional<PromoEntity> getByCodeAndValid(String code);

    @Query("select count (p) > 0 from PromoEntity p " +
            "join p.variantProducts vp " +
            "where vp.id = :variantProductId " +
            "and p.isEnable = true " +
            "and p.id != :promoId")
    boolean existsByVariantProductsIdAndIsEnableTrueAndIdNot(Long variantProductId, Long promoId);
}
