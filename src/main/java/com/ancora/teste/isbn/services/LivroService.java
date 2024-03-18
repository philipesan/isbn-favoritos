package com.ancora.teste.isbn.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ancora.teste.isbn.dto.responses.ApiResponseDTO;

@Service
public interface LivroService {
	ResponseEntity<ApiResponseDTO> buscaLivro(Long isbn);
}
