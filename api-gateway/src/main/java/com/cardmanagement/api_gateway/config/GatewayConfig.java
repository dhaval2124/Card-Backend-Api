package com.cardmanagement.api_gateway.config;

import com.cardmanagement.api_gateway.filters.AuthFilter;
import com.cardmanagement.api_gateway.filters.LoggingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class GatewayConfig {

    @Autowired
    private AuthFilter authFilter;

    @Autowired
    private LoggingFilter loggingFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // User Service Routes
                .route("user-service", r -> r
                        .path("/api/users/**")
                        .filters(f -> f
                                .filter(loggingFilter.apply(new LoggingFilter.Config()))
                                .filter(authFilter.apply(new AuthFilter.Config()))
                                .rewritePath("/api/users/(?<segment>.*)", "/api/users/${segment}")
                                .addRequestHeader("X-Gateway", "api-gateway")
                        )
                        .uri("lb://user-service")
                )

                // Card Service Routes
                .route("card-service", r -> r
                        .path("/api/cards/**")
                        .filters(f -> f
                                .filter(loggingFilter.apply(new LoggingFilter.Config()))
                                .filter(authFilter.apply(new AuthFilter.Config()))
                                .rewritePath("/api/cards/(?<segment>.*)", "/api/cards/${segment}")
                                .addRequestHeader("X-Gateway", "api-gateway")
                        )
                        .uri("lb://card-service")
                )

                // Transaction Service Routes
                .route("transaction-service", r -> r
                        .path("/api/transactions/**")
                        .filters(f -> f
                                .filter(loggingFilter.apply(new LoggingFilter.Config()))
                                .filter(authFilter.apply(new AuthFilter.Config()))
                                .rewritePath("/api/transactions/(?<segment>.*)", "/api/transactions/${segment}")
                                .addRequestHeader("X-Gateway", "api-gateway")
                        )
                        .uri("lb://transaction-service")
                )

                // Payment Service Routes
                .route("payment-service", r -> r
                        .path("/api/payments/**")
                        .filters(f -> f
                                .filter(loggingFilter.apply(new LoggingFilter.Config()))
                                .filter(authFilter.apply(new AuthFilter.Config()))
                                .rewritePath("/api/payments/(?<segment>.*)", "/api/payments/${segment}")
                                .addRequestHeader("X-Gateway", "api-gateway")
                        )
                        .uri("lb://payment-service")
                )

                // Notification Service Routes
                .route("notification-service", r -> r
                        .path("/api/notifications/**")
                        .filters(f -> f
                                .filter(loggingFilter.apply(new LoggingFilter.Config()))
                                .rewritePath("/api/notifications/(?<segment>.*)", "/api/notifications/${segment}")
                                .addRequestHeader("X-Gateway", "api-gateway")
                        )
                        .uri("lb://notification-service")
                )

                // Fraud Detection Service Routes
                .route("fraud-detection-service", r -> r
                        .path("/api/fraud/**")
                        .filters(f -> f
                                .filter(loggingFilter.apply(new LoggingFilter.Config()))
                                .filter(authFilter.apply(new AuthFilter.Config()))
                                .rewritePath("/api/fraud/(?<segment>.*)", "/api/fraud/${segment}")
                                .addRequestHeader("X-Gateway", "api-gateway")
                        )
                        .uri("lb://fraud-detection-service")
                )

                // Health Check Routes (no authentication required)
                .route("health-check", r -> r
                        .path("/actuator/**")
                        .filters(f -> f.filter(loggingFilter.apply(new LoggingFilter.Config())))
                        .uri("lb://api-gateway")
                )

                // Public authentication routes
                .route("auth-routes", r -> r
                        .path("/api/auth/**")
                        .filters(f -> f
                                .filter(loggingFilter.apply(new LoggingFilter.Config()))
                                .rewritePath("/api/auth/(?<segment>.*)", "/api/users/${segment}")
                        )
                        .uri("lb://user-service")
                )

                .build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOriginPatterns(Arrays.asList("*"));
        corsConfig.setMaxAge(3600L);
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(Arrays.asList("*"));
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}