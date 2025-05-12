package br.com.chayotte;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Chayotte API",
		version = "0.0.1",
		description = """
				Chayotte is a comprehensive logistics management platform designed to help retailers across various segments streamline their operations.
				 Built with a modular monolithic architecture, Chayotte provides a robust set of features to manage products, orders, deliveries, and more.
				"""
))
@SecurityScheme(
		name = "JWT",
		description = "Enter JWT token with Bearer prefix, e.g. 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...'",
		scheme = "bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		in = SecuritySchemeIn.HEADER
)
public class ChayotteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChayotteApplication.class, args);
	}

}
