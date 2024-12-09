package com.kaolee.hotel.configuration;


import com.kaolee.hotel.controller.login.LoginFailHandler;
import com.kaolee.hotel.controller.login.LoginSuccessHandler;
import com.kaolee.hotel.controller.login.usernameLogin.UsernameAuthenticationFilter;
import com.kaolee.hotel.controller.login.usernameLogin.UsernameAuthenticationProvider;
import com.kaolee.hotel.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.ProviderManager;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final ApplicationContext applicationContext;
    private final JwtFilter jwtFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // 禁用 CSRF（僅在開發環境建議）
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/api/v1/user/signup/**",//註冊路路徑
                         "/api-docs/**",  // Swagger 的 API 文檔路徑
                         "/swagger-ui/**",   // Swagger UI 的靜態資源
                         "/swagger-ui.html"  // Swagger UI 主頁
                ).permitAll() // 公開路由
                    //角色權限驗證還沒做
//                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()             // 其他路由需要驗證
            );
              /*  //前端請求
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll())          // 啟用登入表單
                .logout(logout -> logout
                        .logoutUrl("/api/v1/user/logout")
                        .logoutSuccessUrl("/public/home")         // 登出後跳轉的頁面
                        .permitAll() );
*/
        //jwt校驗
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        LoginSuccessHandler loginSuccessHandler = applicationContext.getBean(LoginSuccessHandler.class);
        LoginFailHandler loginFailHandler = applicationContext.getBean(LoginFailHandler.class);

        //增加驗證方式:帳號密碼
        UsernameAuthenticationFilter usernameLoginFilter = new UsernameAuthenticationFilter(
                new AntPathRequestMatcher("/api/v1/user/login", HttpMethod.POST.name()),
                new ProviderManager(
                        List.of(applicationContext.getBean(UsernameAuthenticationProvider.class))),
                loginSuccessHandler,
                loginFailHandler);
        http.addFilterBefore(usernameLoginFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
