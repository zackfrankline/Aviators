package com.aviator.subscription_service.config;

import com.aviator.subscription_service.model.Role;
import com.aviator.subscription_service.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf( csrf -> csrf.disable())
                .authorizeHttpRequests(authorization -> authorization
//                        ./ 1. EXACT MATCHES FIRST (More specific)
                        .requestMatchers(HttpMethod.GET, "/api/subscriptions/categories").hasAuthority(Role.ROLE_ADMIN.toString())
                        .requestMatchers(HttpMethod.GET, "/api/subscriptions").hasAuthority(Role.ROLE_ADMIN.toString())

                        // 2. DYNAMIC MATCHES SECOND (Less specific)
                        .requestMatchers(HttpMethod.GET, "/api/subscriptions/{userId}").hasAuthority(Role.ROLE_AUDIENCE.toString())
                        .requestMatchers(HttpMethod.POST, "/api/subscriptions/{categoryId}").hasAuthority(Role.ROLE_AUDIENCE.toString())
                        .requestMatchers(HttpMethod.DELETE,"/api/subscriptions/{categoryId}").hasAuthority(Role.ROLE_AUDIENCE.toString())
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
