package com.example.bak.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        String jwt = "JWT";
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
                .name(jwt)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        );
        return new OpenAPI().components(new Components()).info(apiInfo())
                .addSecurityItem(apiSecurityRequirement()).components(components);
    }

    private Info apiInfo() {
        return new Info()
                .title("Jobmanri Server API")
                .description("잡만리 서버 API Swagger 입니다 🚀")
                .version("0.1.0");
    }

    private SecurityRequirement apiSecurityRequirement() {
        String jwt = "JWT";
        return new SecurityRequirement().addList(jwt);
    }
}
