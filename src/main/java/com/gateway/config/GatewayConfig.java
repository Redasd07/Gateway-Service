package com.gateway.config;

import com.gateway.security.JwtAuthenticationGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, JwtAuthenticationGatewayFilterFactory jwtAuthFilter) {
        return builder.routes()
                .route("auth-service", r -> r.path("/api/auth/**")
                        .uri("http://localhost:8081"))
                .route("user-service", r -> r.path("/api/users/**")
                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthenticationGatewayFilterFactory.Config())))
                        .uri("http://localhost:8082"))
                .build();
    }
}
