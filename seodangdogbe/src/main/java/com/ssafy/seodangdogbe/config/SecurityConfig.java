package com.ssafy.seodangdogbe.config;

import com.ssafy.seodangdogbe.exception.JWTAccessDeniedHandler;
import com.ssafy.seodangdogbe.jwt.JWTAuthenticationFilter;
import com.ssafy.seodangdogbe.jwt.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWTProvider jwtProvider;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(authz -> authz
//                        .requestMatchers("/api/myword/**").permitAll()
//                        .requestMatchers("/api/game/**").permitAll()
//                        .requestMatchers("/api/main/**").permitAll()
//                        .requestMatchers("/api/news/**").permitAll()
//                        .requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/api-docs/**", "/swagger-ui/**", "/swagger-resources/**", "/webjars/**").permitAll()
//                        .anyRequest().authenticated()
//                );
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        // cors 설정 custom 한대로 넣어주기
        httpSecurity.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));

        httpSecurity
                // token 방식이므로 csrf 막음
                .csrf(AbstractHttpConfigurer::disable)
                // token 방식이므로 session 막음
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // login, join은 인증없이 접근가능하다
                // 그 외 조인은 인증없이 접근 불가능하다
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/api/main/**").permitAll()
                                .requestMatchers("/login", "/join").permitAll()
                                .requestMatchers("/","/swagger-ui.html", "/v3/api-docs/**", "/api-docs/**", "/swagger-ui/**", "/swagger-resources/**", "/webjars/**").permitAll()
                                .anyRequest().hasRole("USER")
                )
                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig.authenticationEntryPoint(new com.example.seodangdogbe.config.security.CustomeAuthenticationEntryPoint()).accessDeniedHandler( new JWTAccessDeniedHandler()))
                .addFilterBefore(new JWTAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(false);
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080", "http://localhost:8081","https://localhost:3000", "https://localhost:8080", "https://localhost:8081" ,"http://localhost:5000", "https://localhost:5000" ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }
}

