package com.qorvia.apigateway.config.route;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("account-service", r -> r.path("/account/**")
                        .uri("lb://ACCOUNT-SERVICE"))
                .route("blog-feedback-service", r -> r.path("/blog-feedback/**")
                        .uri("lb://BLOG-FEEDBACK-SERVICE"))
                .route("communication-service", r -> r.path("/communication/**")
                        .uri("lb://COMMUNICATION-SERVICE"))
                .route("event-management-service", r -> r.path("/event/**")
                        .uri("lb://EVENT-MANAGEMENT-SERVICE"))
                .route("notification-service", r -> r.path("/notification/**")
                        .uri("lb://NOTIFICATION-SERVICE"))
                .route("payment-service", r -> r.path("/payment/**")
                        .uri("lb://PAYMENT-SERVICE"))
                .build();
    }
}
