package com.ancora.teste.isbn;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.ancora.teste.isbn.entities.User;
import com.ancora.teste.isbn.repositories.UserRepository;
import com.ancora.teste.isbn.configs.RsaKeyProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class IsbnApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsbnApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	    return builder.build();
	}
	

	@Bean
	CommandLineRunner commandLineRunner(UserRepository usersRepo, PasswordEncoder encoder) {
		return args -> {
			usersRepo.save(User.builder()
								.userName("userdev")
								.password(encoder.encode("userdev"))
								.roles("ROLE_USER")
								.build());
			
			usersRepo.save(User.builder()
					.userName("admindev")
					.password(encoder.encode("admindev"))
					.roles("ROLE_USER, ROLE_ADMIN")
					.build());
		};
	}
}
