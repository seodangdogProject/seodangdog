package com.ssafy.seodangdogbe.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        Server localhost = new Server();
        localhost.setUrl("http://localhost:8081");
        Server server = new Server();
        server.setUrl("https://j10e104.p.ssafy.io");
        return new OpenAPI()
                .info(new Info()
                        .title("seodangdog API")
                        .description("Sample Application API description")
                        .version("v1.0.0"))
                .servers(List.of(localhost, server));
    }
}
// http://127.0.0.1:8081/swagger-ui.html
