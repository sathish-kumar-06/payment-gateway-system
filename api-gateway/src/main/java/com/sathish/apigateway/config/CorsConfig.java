package com.sathish.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        // Allow requests from your React app's origin
        corsConfig.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfig.setMaxAge(3600L); // Cache the preflight response for 1 hour
        corsConfig.addAllowedMethod("*"); // Allow all HTTP methods (GET, POST, OPTIONS, etc.)
        corsConfig.addAllowedHeader("*"); // Allow all headers, including your custom 'Idempotency-Key'

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig); // Apply this CORS configuration to all paths

        return new CorsWebFilter(source);
    }
}