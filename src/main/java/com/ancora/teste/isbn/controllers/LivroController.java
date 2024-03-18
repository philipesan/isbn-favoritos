package com.ancora.teste.isbn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ancora.teste.isbn.dto.responses.ApiResponseDTO;
import com.ancora.teste.isbn.services.LivroService;


@RestController
@RequestMapping(path="api/v1/livro")
public class LivroController {
	
	@Autowired
	LivroService livroService;
	
	@GetMapping(path=("/busca/{isbn}"))
	@PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
	public ResponseEntity<ApiResponseDTO> listaDocumentos(@PathVariable Long isbn) {
		return livroService.buscaLivro(isbn);
	}
}
