package com.ssafy.seodangdogbe.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.apache.catalina.security.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import java.util.List;



@Configuration
@SecurityScheme(
        name = "accessToken",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@OpenAPIDefinition
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {

        Server localhost = new Server();
        localhost.setUrl("http://localhost:8081");
        Server server = new Server();
        server.setUrl("https://j10e104.p.ssafy.io");

        HeaderParameter authorizationHeader = new HeaderParameter();
        authorizationHeader.setName("Authorization");
        authorizationHeader.setDescription("Access Token");
        authorizationHeader.setRequired(false);
        authorizationHeader.setSchema(new io.swagger.v3.oas.models.media.StringSchema());
        return new OpenAPI()
                .info(new Info()
                        .title("seodangdog API")
                        .description("Sample Application API description")
                        .version("v1.0.0"))
                .servers(List.of(localhost, server))
                .addSecurityItem(new SecurityRequirement().addList("accessToken"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addParameters("Authorization", authorizationHeader));
    }



}
