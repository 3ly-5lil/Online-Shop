package com.ak.online_shop.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {

	/**
	 * Application entry point that starts the Spring Boot API gateway.
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}