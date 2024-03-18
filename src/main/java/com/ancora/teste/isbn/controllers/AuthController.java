package com.ancora.teste.isbn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ancora.teste.isbn.dto.responses.ApiResponseDTO;
import com.ancora.teste.isbn.services.TokenService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path="api/v1/auth")
@Slf4j
public class AuthController {
	@Autowired TokenService tokenService;
	
	@PostMapping("/token")
	ResponseEntity<ApiResponseDTO> getToken(Authentication authentication) {
		log.debug("Login solicitado pelo usuario: '{}'", authentication.getName());
		return tokenService.generateToken(authentication);
	}
}