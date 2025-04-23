package com.example.servicemarketplace.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.*;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI api() {
    return new OpenAPI()
        .info(new Info()
        .title("servicemarketplace API")
        .description("API documentation for my Spring Boot application.")
        .termsOfService("https://example.com/terms")
        .contact(new Contact()
          .name("John Doe")
          .url("https://example.com/contact")
          .email("johndoe@example.com"))
        .license(new License()
          .name("Apache 2.0")
          .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
  }

}
