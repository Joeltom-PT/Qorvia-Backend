package com.qorvia.apigateway.config;


import com.qorvia.apigateway.filter.LoggingFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGateWayConfig {
    @Bean
    public GlobalFilter globalFilter() {
        return new LoggingFilter();
    }
}