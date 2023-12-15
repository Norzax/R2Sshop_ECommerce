package com.aclass.r2sshop_ecommerce.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//    @Bean
//    public Docket postApi(){
//        return new Docket(DocumentationType.SWAGGER_2).groupName("api")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.aclass.r2sshop_ecommerce.controllers"))
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(apiInfo());
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("R2Sshop ECommerce API")
//                .description("API reference for developers")
//                .version("1.0")
//                .build();
//    }
//}
