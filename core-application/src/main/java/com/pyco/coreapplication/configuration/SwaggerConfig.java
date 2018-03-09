package com.pyco.coreapplication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
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

     static final String ENDPOINT_PACKAGE_NAME = "com.pyco.coreapplication.endpoint";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(ENDPOINT_PACKAGE_NAME))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "TODO LIST REST API",
                "Rest API for customer to create todo of their own",
                "API V1",
                "Terms of service",
                new Contact("Pham Tien Dat", "www.todolist.com", "todolist@company.com"),
                "Todo List License", "todo-list-license.com", Collections.emptyList());
    }
}
