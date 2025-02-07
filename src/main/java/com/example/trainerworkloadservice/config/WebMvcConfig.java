package com.example.trainerworkloadservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * OpenApi config.
     */
    @Bean
    public OpenAPI customOpenApi() {
        log.debug("Configuring Custom OpenApi");
        return new OpenAPI()
            .servers(List.of(
                    new Server().url("http://localhost:8090")
                )
            )
            .info(
                new Info().title("Trainer Workload API.")
                    .description("Documentation for Trainer Workload API.")
                    .version("1.0.0")
            );
    }
}
