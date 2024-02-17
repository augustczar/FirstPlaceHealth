package com.firstplacehealth.health.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI customOpenApi() {
		return new OpenAPI()
				.components(new Components())
					.info(new Info().title("Saúde em primeiro lugar")
						.description("API Cadastro de Beneficíarios"));
	}
}
