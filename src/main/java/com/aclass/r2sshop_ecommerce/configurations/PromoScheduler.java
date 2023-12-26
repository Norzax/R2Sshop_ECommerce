package com.aclass.r2sshop_ecommerce.configurations;

import com.aclass.r2sshop_ecommerce.models.entity.CartLineItemEntity;
import com.aclass.r2sshop_ecommerce.models.entity.PromoEntity;
import com.aclass.r2sshop_ecommerce.models.entity.VariantProductEntity;
import com.aclass.r2sshop_ecommerce.repositories.CartLineItemRepository;
import com.aclass.r2sshop_ecommerce.repositories.PromoRepository;
import com.aclass.r2sshop_ecommerce.repositories.VariantProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class PromoScheduler {

    private final PromoRepository promoRepository;
    private final CartLineItemRepository cartLineItemRepository;

    @Autowired
    public PromoScheduler(PromoRepository promoRepository, CartLineItemRepository cartLineItemRepository) {
        this.promoRepository = promoRepository;
        this.cartLineItemRepository = cartLineItemRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void updateExpiredPromos() {
        promoRepository.updateExpiredPromos();
    }

    @Scheduled(cron = "0 * * * * *")
    public void updateCartLineItemTotalPrices() {
        List<CartLineItemEntity> cartLineItems = cartLineItemRepository.findAll();

        for (CartLineItemEntity cartLineItem : cartLineItems) {
            boolean updatedNewPrice = false;
            for (PromoEntity promo : cartLineItem.getVariantProduct().getPromos()) {
                if (!cartLineItem.getIsDeleted() && promo != null && promo.getEndDate().after(new Date()) && promo.getStartDate().before(new Date()) && promo.getUsageLimit() != 0 && promo.getIsEnable()) {
                    double newPrice = calculateNewPrice(cartLineItem, promo);
                    cartLineItem.setTotalPrice(newPrice * cartLineItem.getQuantity());
                    updatedNewPrice = true;
                    break;
                }
            }

            if (!updatedNewPrice) {
                if (cartLineItem.getTotalPrice() != null && !cartLineItem.getIsDeleted()) {
                    cartLineItem.setTotalPrice(calculateOldPrice(cartLineItem) * cartLineItem.getQuantity());
                }
            }

            cartLineItemRepository.save(cartLineItem);
        }
    }

    private double calculateNewPrice(CartLineItemEntity cartLineItem, PromoEntity promo) {
        VariantProductEntity variantProduct = cartLineItem.getVariantProduct();
        double discountPercentage = promo.getDiscountPercentage();
        double price = variantProduct.getPrice();
        return price - (price * discountPercentage / 100);
    }

    private double calculateOldPrice(CartLineItemEntity cartLineItem) {
        VariantProductEntity variantProduct = cartLineItem.getVariantProduct();
        return variantProduct.getPrice();
    }
}
