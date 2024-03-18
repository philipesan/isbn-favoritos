package com.ancora.teste.isbn.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ancora.teste.isbn.dto.responses.ApiResponseDTO;

@Service
public interface FavoritoService {
	
	ResponseEntity<ApiResponseDTO> adicionarFavorito(Long id, String isbn);

	ResponseEntity<ApiResponseDTO> removerFavorito(Long id, String isbn);
}
