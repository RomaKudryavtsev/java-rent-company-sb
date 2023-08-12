package com.tel_ran.rent_company.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.format.DateTimeFormatter;

@Configuration
@EnableSwagger2
public class AppConfig {
    @Value("${rent.date.format}")
    String format;

    @Bean
    public DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern(format);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tel_ran.rent_company"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .tags(new Tag("Cars", "Operations with cars"),
                        new Tag("Models", "Operations with models"),
                        new Tag("Drivers", "Operations with drivers"),
                        new Tag("Records", "Operations with records"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("RentCompany-SB")
                .description("Simple auto rent company server")
                .version("2.0.0")
                .build();
    }
}
