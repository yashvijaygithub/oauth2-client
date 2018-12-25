package com.yash.oauth_client.config;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {
    @Value("${application.swagger.basePackage}")
    private String basePackage;

    @Value("${application.swagger.name}")
    private String name;

    @Value("${application.swagger.description}")
    private String description;

    @Value("${application.swagger.termsOfServiceUrl}")
    private String termsOfServiceUrl;

    @Value("${application.swagger.version}")
    private String version;

    @Value("${application.swagger.contact.name}")
    private String contact;

    @Value("${application.swagger.contact.email}")
    private String email;

    @Value("${application.swagger.licence}")
    private String licence;

    @Value("${application.swagger.licenceUrl}")
    private String licenceUrl;

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(name)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .version(version)
                .contact(new Contact(contact, null, email))
                .license(licence)
                .licenseUrl(licenceUrl)
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
                .enableUrlTemplating(true);
    }
}
