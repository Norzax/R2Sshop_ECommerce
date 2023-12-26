package com.aclass.r2sshop_ecommerce.repositories;

import com.aclass.r2sshop_ecommerce.models.entity.CartLineItemEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartLineItemRepository extends JpaRepository<CartLineItemEntity, Long> {
    @Query("SELECT clt FROM CartLineItemEntity clt WHERE clt.variantProduct.id = :variantProductId AND clt.cart.id = :cartId AND clt.isDeleted = false")
    Optional<CartLineItemEntity> getExistCartLineItem(@Param("variantProductId") Long variantProductId, @Param("cartId") Long cartId);
    @Query("select count(clt) from CartLineItemEntity clt where clt.cart.id = :cartId and clt.isDeleted = false")
    int getIfCartHaveItem(Long cartId);
    @Query("select clt.order.id from CartLineItemEntity clt where clt.cart.id = :cartId and clt.isDeleted = false")
    Long getExistOrderIdByCarId(Long cartId);
    @Query("select sum(clt.totalPrice) from CartLineItemEntity clt where clt.order.id = :orderId and clt.isDeleted = false")
    Double getTotalPrice(Long orderId);
    @Transactional
    @Modifying
    @Query("update CartLineItemEntity clt set clt.isDeleted = true where clt.order.id = :orderId")
    void softDelete(Long orderId);
    @Query("select clt from CartLineItemEntity clt  where clt.cart.user.id = :userId and clt.isDeleted = false")
    List<CartLineItemEntity> findByCart_User_Id(Long userId);
    @Transactional
    @Modifying
    @Query("DELETE FROM CartLineItemEntity c WHERE c.variantProduct.id = :variantProductId AND c.cart.id = :cartId")
    void deleteAllByVariantProduct_IdAndCart_Id(Long variantProductId, Long cartId);
}
