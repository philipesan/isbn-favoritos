package com.ancora.teste.isbn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ancora.teste.isbn.dto.responses.ApiResponseDTO;
import com.ancora.teste.isbn.services.FavoritoService;

@RestController
@RequestMapping(path="api/v1/favorito")
public class FavoritoController {
	@Autowired
	FavoritoService favoritoService;
	
	@PatchMapping("/adicionar/{id}/{isbn}")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
	public ResponseEntity<ApiResponseDTO> adicionarFavorito(@PathVariable Long id, @PathVariable String isbn) {
		return favoritoService.adicionarFavorito(id, isbn);
	}
	
	@PatchMapping("/remover/{id}/{isbn}")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
	public ResponseEntity<ApiResponseDTO> removerFavorito(@PathVariable Long id, @PathVariable String isbn) {
		return favoritoService.removerFavorito(id, isbn);
	}
}
