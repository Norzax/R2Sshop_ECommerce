package com.aclass.r2sshop_ecommerce.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public static ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
