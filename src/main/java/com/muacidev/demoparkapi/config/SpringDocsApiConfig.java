package com.muacidev.demoparkapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocsApiConfig {

    @Bean
    public OpenAPI openAPI(){


        return new OpenAPI()
                .components(new Components().addSecuritySchemes("Security", securityScheme()))
                .info(
                        new Info()
                                .title("REST API - Spring Park")
                                .description("API para gestão de estacionamento de veiculos")
                                .version("v1")
                                .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
                                .contact(new Contact().name("Muaci José").email("muaci@yahoo.com"))

                );
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .description( "Insira um bearer token validao para prosseguir" )
                .type( SecurityScheme.Type.HTTP )
                .in( SecurityScheme.In.HEADER )
                .scheme( "bearer" )
                .bearerFormat( "JWT" )
                .name("Security");
    }
}
