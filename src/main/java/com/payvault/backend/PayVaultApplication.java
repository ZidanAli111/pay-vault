package com.payvault.backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PayVaultApplication {

	public static void main(String[] args) {


		Dotenv dotenv=Dotenv.load();

		System.setProperty("SERVER_PORT",dotenv.get("SERVER_PORT"));
		System.setProperty("POSTGRES_URL",dotenv.get("POSTGRES_URL"));
		System.setProperty("POSTGRES_USER",dotenv.get("POSTGRES_USER"));
		System.setProperty("POSTGRES_PASSWORD",dotenv.get("POSTGRES_PASSWORD"));
		System.setProperty("MONGODB_URI",dotenv.get("MONGODB_URI"));
		System.setProperty("REDIS_HOST",dotenv.get("REDIS_HOST"));
		System.setProperty("REDIS_PORT",dotenv.get("REDIS_PORT"));
		System.setProperty("JWT_SECRET",dotenv.get("JWT_SECRET"));
		System.setProperty("JWT_EXPIRATION",dotenv.get("JWT_EXPIRATION"));
		System.setProperty("GOOGLE_CLIENT_ID",dotenv.get("GOOGLE_CLIENT_ID"));
		System.setProperty("GOOGLE_CLIENT_SECRET",dotenv.get("GOOGLE_CLIENT_SECRET"));

		SpringApplication.run(PayVaultApplication.class, args);
	}

}
