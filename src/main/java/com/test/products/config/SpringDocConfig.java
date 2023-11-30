package com.test.products.config;



import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI SpringDocConfig() {
        return new OpenAPI()
                .info(new Info().title("Prueba-Tecnica-PayGoal")
                        .description("Test PayGoal App")
                        .version("v0.0.1")
                        .license(new License().name("Test java 17, Spring boot 3.20")))
                .externalDocs(new ExternalDocumentation()
                        .description("Portfolio Abel Acevedo v1")
                        .url("https://www.abelacevedo.com.ar"));
                /*
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .in(SecurityScheme.In.HEADER)
                                        .scheme("bearer").bearerFormat("JWT")));

                 */
    }

}
