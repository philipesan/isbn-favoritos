package com.ancora.teste.isbn.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ancora.teste.isbn.dto.responses.ApiResponseDTO;
import com.ancora.teste.isbn.entities.Livro;

@Service
public interface LivroService {
	ResponseEntity<ApiResponseDTO> buscaLivro(String isbn);

	Livro encontraLivro(String isbn) throws Exception;
}
