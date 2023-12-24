package com.aclass.r2sshop_ecommerce.configurations;

import com.aclass.r2sshop_ecommerce.repositories.PromoRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class PromoScheduler {

    private final PromoRepository promoRepository;

    public PromoScheduler(PromoRepository promoRepository) {
        this.promoRepository = promoRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void updateExpiredPromos() {
        promoRepository.updateExpiredPromos();
    }
}
