package com.Infraestructura.Infraestructura.Config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfraestructuraConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Infraestructura API")
                        .version("1.0.")
                        .description("API para la gestión de Infraestructura de la sede"));
    }

}
