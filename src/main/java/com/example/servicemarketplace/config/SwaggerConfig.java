package com.example.servicemarketplace.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.*;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI api() {
    return new OpenAPI()
        .info(new Info()
        .title("Service Marketplace API")
        .description("API documentation for the Service Marketplace application.")
        .termsOfService("https://example.com/terms")
        .contact(new Contact()
          .name("John Doe")
          .url("https://example.com/contact")
          .email("johndoe@example.com"))
        .license(new License()
          .name("Apache 2.0")
          .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
            .components(new Components()
                    .addSecuritySchemes("basicAuth",
                            new SecurityScheme()
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("basic")))
            .addSecurityItem(new SecurityRequirement().addList("basicAuth"));
  }

}
