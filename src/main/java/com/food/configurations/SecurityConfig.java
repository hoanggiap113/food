package com.food.configurations;

import com.food.dto.response.UserDetailResponse;
import com.food.security.JwtFilter;
import com.food.services.JwtService;
import com.food.services.IUserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Getter
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final JwtService jwtService;
    private final IUserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Cho phép truy cập toàn bộ /auth/**
                        .requestMatchers("/user/**", "/product/**").hasRole("ADMIN") // Chỉ ADMIN truy cập /user/**
                        .anyRequest().authenticated() // Bảo vệ các yêu cầu khác
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/auth/oauth2-login") // Trang login OAuth2
                        .successHandler((request, response, authentication) -> {
                            DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
                            String email = oidcUser.getEmail();
                            String name = oidcUser.getName();
                            UserDetailResponse user = userService.saveOrUpdateGoogleUser(email, name);
                            String jwt = jwtService.generateToken(user.getEmail(), Collections.singletonList(user.getRole()));
                            response.sendRedirect("/auth/google-callback?token=" + jwt);
                        })
                        .failureHandler((request, response, exception) -> {
                            response.sendRedirect("/auth/oauth2-login?error=" + URLEncoder.encode(exception.getMessage(), StandardCharsets.UTF_8));
                        })
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}


