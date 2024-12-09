package com.kaolee.hotel.configuration;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.ui.AbstractSwaggerResourceResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Bearer Token 設定
        SecurityScheme bearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("Authorization")
                .in(SecurityScheme.In.HEADER);

        return new OpenAPI()
                .info(new Info()
                        .title("Hotel API Documentation")
                        .version("1.0.0")
                        .description("Hotel API with JWT and API Token authentication"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", bearerScheme)) // 加入 Bearer Token 設定
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")); // 將 Bearer 授權應用於全局
    }
}
