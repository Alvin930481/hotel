package com.kaolee.hotel.configuration;

import com.kaolee.hotel.interceptor.JwtTokenInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//TODO:因為會攔截到swagger，之後再處理
//@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
//    @Autowired
    private JwtTokenInterceptor jwtTokenInterceptor;
    /**
     * 註冊自定義攔截器
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("開始註冊自定義攔截器...");
        registry.addInterceptor(jwtTokenInterceptor)
                .addPathPatterns("/api/v1/user/**")
                .excludePathPatterns("/api/v1/user/login","/api/v1/user/signup");
    }

}
