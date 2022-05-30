package org.ratseno.demo.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .securityContexts(Arrays.asList(this.securityContext()))
                //.securitySchemes(Arrays.asList(apiKey()))
                .securitySchemes(Arrays.asList(
                                                new ApiKey("X-ACCESS-TOKEN", "Authorization", "header"), 
                                                new ApiKey("X-REFRESH-TOKEN", "Authorization", "header")
                                            ))
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.ratseno.demo"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Practice Swagger")
                .description("practice swagger config")
                .version("1.0")
                .build();
    }    
    
    //JWT를 인증 헤더로 포함하도록 APIKey를 정의해
    /* 
    private ApiKey apiKey(){
        return new ApiKey("Authorization", "Authorization", "header");
    }
    */

    //글로벌 Authorization Scope를 사용하여 JWT Security Context를 구성
    private SecurityContext securityContext(){
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope; 
        return Arrays.asList(
                                new SecurityReference("X-ACCESS-TOKEN", authorizationScopes), 
                                new SecurityReference("X-REFRESH-TOKEN", authorizationScopes)
                            ); 
    }
}
