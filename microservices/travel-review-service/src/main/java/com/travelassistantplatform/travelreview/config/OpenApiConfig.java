package com.travelassistantplatform.travelreview.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Travel review service API").version("0.1").description(
                        "This is the travel review service.").contact(new Contact().name("Zhiyuan Yao").email("zhiyuan.yao@sjsu.edu")));
    }
}
