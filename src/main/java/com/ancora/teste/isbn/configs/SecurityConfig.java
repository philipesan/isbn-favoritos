package com.ancora.teste.isbn.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	
	@Autowired RsaKeyProperties rsaKeys;
	
    @Bean
    @Order(1)
    public SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
        return http
        		.securityMatcher("/api/v1/usuario/**"
        				, "/api/v1/livro/**"
        				, "/api/v1/favorito/**")
        		.csrf(csrf -> csrf
        				.ignoringRequestMatchers("/h2-console/**"))
		        .authorizeHttpRequests(auth -> auth
		        		.requestMatchers("/api/v1/auth/**", "/h2-console/**").permitAll()
		                .anyRequest().authenticated())
		        .headers(headers -> headers.frameOptions().sameOrigin())
		        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
        		.build();
    }
	
    @Bean
    @Order(2)
    SecurityFilterChain basicAuthSecurityFilterChain(HttpSecurity http) throws Exception {

        return http
        		.securityMatcher("/api/v1/auth/**")
        		.csrf(csrf -> csrf
        				.ignoringRequestMatchers("/api/v1/auth/**"))
		        .authorizeHttpRequests(auth -> auth
		        		.requestMatchers("/api/v1/auth/**").permitAll()
		                .anyRequest().authenticated())
		        .httpBasic(Customizer.withDefaults())
		        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.build();
    }
    
    @Bean
    @Order(3)
    SecurityFilterChain h2ConsoleSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                .authorizeHttpRequests( auth -> {
                    auth.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll();
                })
                .csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")))
                .headers(headers -> headers.frameOptions().disable())
                .build();
    }
	
	
	@Bean
	JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
	}
	
	@Bean
	JwtEncoder jwtEncoder() {
		JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
		JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk) );
		return new NimbusJwtEncoder(jwks);
	}
	
	@Bean
	PasswordEncoder passEncoder() {
		return new BCryptPasswordEncoder();
		
	}
}
