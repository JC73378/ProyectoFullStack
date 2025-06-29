package com.Notificaciones.cl.Notificaciones_JGX.Config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportesConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Notificaciones JGX API")
                        .version("1.0.")
                        .description("API para la gestión de notificaciones JGX"));
    }

}
