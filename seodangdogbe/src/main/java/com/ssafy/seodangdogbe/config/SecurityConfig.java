package com.ssafy.seodangdogbe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/myword/**").permitAll()
                        .requestMatchers("/api/game/**").permitAll()
                        .requestMatchers("/api/main/**").permitAll()
                        .requestMatchers("/api/news/**").permitAll()
                        .requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/api-docs/**", "/swagger-ui/**", "/swagger-resources/**", "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}

