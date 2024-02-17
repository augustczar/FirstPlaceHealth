package com.firstplacehealth.health;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Saúde em primeiro lugar", version = "1.0.0", description = "API Cadastro de Beneficíarios"),
		servers = {
				@Server(url = "http://localhost:8082/firstPlaceHealth")
		})
public class FirstPlaceHealthApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstPlaceHealthApplication.class, args);
	}

}
