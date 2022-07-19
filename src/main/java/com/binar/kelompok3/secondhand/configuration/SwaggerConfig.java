package com.binar.kelompok3.secondhand.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.binar.kelompok3.secondhand.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Thriforzo API",
                "API for 'Thriforzo' Website",
                "1.0",
                "Private use Only",
                new Contact("Thriforzo", "https://develop-secondhand-fejs-3.netlify.app/", "email@email.com"),
                "API License",
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
                Collections.emptyList()
        );
    }
}
