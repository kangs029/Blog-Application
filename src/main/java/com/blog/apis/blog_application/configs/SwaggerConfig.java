package com.blog.apis.blog_application.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                  .info(new Info().title("Blog App API")
                                  .description("This is blog application project api development"));
                                //   .contact(new Contact().name("Kangs").email("kangs2@gmail.com"))
                                //   .license(new License().name("Apache"));
    }
}
