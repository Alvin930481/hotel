package com.kaolee.hotel.configuration;

import com.kaolee.hotel.utils.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 禁用 CSRF（僅在開發環境建議）
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/user/login/**",//登入的路徑
                                "/api/v1/user/signup/**",//註冊路路徑
                                 "/api-docs/**",  // Swagger 的 API 文檔路徑
                                 "/swagger-ui/**",   // Swagger UI 的靜態資源
                                 "/swagger-ui.html"  // Swagger UI 主頁
                        ).permitAll() // 公開路由
                        .anyRequest().authenticated()             // 其他路由需要驗證
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(Customizer.withDefaults())          // 啟用預設登入表單
                .logout(Customizer.withDefaults());            // 啟用預設登出
//                有前端的時用到
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .permitAll())          // 啟用預設登入表單
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/public/home")         // 登出後跳轉的頁面
//                        .permitAll() );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder
                .userDetailsService(username ->
                        new org.springframework.security.core.userdetails.User(
                                username,
                                passwordEncoder.encode("password"),
                                new ArrayList<>()
                        )
                )
                .passwordEncoder(passwordEncoder);
        return authManagerBuilder.build();
    }
}
