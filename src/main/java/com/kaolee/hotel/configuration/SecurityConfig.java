package com.kaolee.hotel.configuration;


import com.kaolee.hotel.controller.login.LoginFailHandler;
import com.kaolee.hotel.controller.login.LoginSuccessHandler;
import com.kaolee.hotel.controller.login.usernameLogin.UsernameAuthenticationFilter;
import com.kaolee.hotel.controller.login.usernameLogin.UsernameAuthenticationProvider;
import com.kaolee.hotel.filter.jwt.JwtFilter;
import com.kaolee.hotel.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {
    private final ApplicationContext applicationContext;
    @Autowired
    private JwtUtil jwtUtil;

    /** 禁用不必要的預設filter */
    private void commonHttpSetting(HttpSecurity http) throws Exception {
        // 禁用SpringSecurity預設filter。這些filter都是非前後端分離專案的產物，目前用不到.
        // 如果要看到加載哪些filter，可以在yml配置文件，將日誌設置成DEBUG模式
        // logging:
        //    level:
        //       org.springframework.security: DEBUG
        // 表單登入/登出、session管理、csrf防護等預設配置，如果不disable。會預設創建預設filter
        http.formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                // requestCache用於重定向，前後端分離專案無需重定向，requestCache也用不上
                .requestCache(cache -> cache
                        .requestCache(new NullRequestCache())
                )
                // 無需给用户一个匿名身份
                .anonymous(AbstractHttpConfigurer::disable);
    }

    /**
     * 登入校驗
     */
    @Bean
    public SecurityFilterChain loginFilterChain(HttpSecurity http) throws Exception {
        log.info("loginFilterChain註冊");
        commonHttpSetting(http);
        http.securityMatcher("/api/v1/user/login")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());

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

    /**
     * JwT校驗
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain JwtFilterChain(HttpSecurity http) throws Exception {
        log.info("JwtFilterChain註冊");
        commonHttpSetting(http);
        http
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(
                                        "/api/v1/user/login",//登錄路徑
                                        "/api/v1/user/signup",//註冊路路徑
                                        "/api-docs/**",  // Swagger 的 API 文檔路徑
                                        "/swagger-ui/**",   // Swagger UI 的靜態資源
                                        "/swagger-ui",
                                        "/swagger-ui.html"  // Swagger UI 主頁
                                ).permitAll() // 公開路由
                                //角色權限驗證還沒做
//                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()             // 其他路由需要驗證
                );


        //jwt校驗
        JwtFilter jwtFilter = new JwtFilter(jwtUtil);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


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
