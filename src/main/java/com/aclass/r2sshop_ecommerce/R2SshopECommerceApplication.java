package com.aclass.r2sshop_ecommerce;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(tags = {
        @Tag(name = "1. Authentication: <User>"),
        @Tag(name = "1. Authentication: <Admin>"),
        @Tag(name = "2. User: <Address>"),
        @Tag(name = "2. User: <Cart>"),
        @Tag(name = "2. User: <Category>"),
        @Tag(name = "2. User: <Order>"),
        @Tag(name = "2. User: <Product>"),
        @Tag(name = "2. User: <User>"),
        @Tag(name = "2. User: <Variant Product>"),
        @Tag(name = "3. Admin: <Address>"),
        @Tag(name = "3. Admin: <Cart>"),
        @Tag(name = "3. Admin: <Cart Line Item>"),
        @Tag(name = "3. Admin: <Category>"),
        @Tag(name = "3. Admin: <Product>"),
        @Tag(name = "3. Admin: <Promote>"),
        @Tag(name = "3. Admin: <Role>"),
        @Tag(name = "3. Admin: <User>"),
        @Tag(name = "3. Admin: <Variant Product>")})
public class R2SshopECommerceApplication {
    public static void main(String[] args) {
        SpringApplication.run(R2SshopECommerceApplication.class, args);
    }

}
