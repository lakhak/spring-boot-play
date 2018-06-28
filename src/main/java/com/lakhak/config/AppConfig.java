package com.lakhak.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lakhak.controller.ZipController;
import com.lakhak.service.FooService;
import com.lakhak.service.ZipService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public FooService fooService() {
        return new FooService();
    }

    @Bean
    public ZipService zipService() {
        return new ZipService(objectMapper());
    }

    @Bean
    public ZipController zipController() {
        return new ZipController(fooService(), zipService());
    }
}
