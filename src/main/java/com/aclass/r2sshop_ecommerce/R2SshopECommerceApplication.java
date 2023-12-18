package com.aclass.r2sshop_ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication (exclude = {SecurityAutoConfiguration.class })
public class R2SshopECommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(R2SshopECommerceApplication.class, args);
    }

}
