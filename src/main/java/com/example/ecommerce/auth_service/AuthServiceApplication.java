package com.example.ecommerce.auth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.SecureRandom;
import java.util.Base64;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
//		byte[] key = new byte[32]; // 256 bits
//		new SecureRandom().nextBytes(key);
//		String secret = Base64.getEncoder().encodeToString(key);
//		System.out.println(secret);

	}

}
