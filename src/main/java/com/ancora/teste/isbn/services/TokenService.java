package com.ancora.teste.isbn.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ancora.teste.isbn.dto.responses.ApiResponseDTO;


@Service
public interface TokenService {

	ResponseEntity<ApiResponseDTO> generateToken(Authentication authentication);

}