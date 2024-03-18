package com.ancora.teste.isbn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ancora.teste.isbn.dto.requests.SearchRequestDTO;
import com.ancora.teste.isbn.dto.requests.UsuarioRequestDTO;
import com.ancora.teste.isbn.dto.responses.ApiResponseDTO;
import com.ancora.teste.isbn.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path="api/v1/usuario")
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;

	@PostMapping("/criar")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
	public ResponseEntity<ApiResponseDTO> createUsuario(@Valid @RequestBody UsuarioRequestDTO usuario) {
		return usuarioService.createUsuario(usuario);
	}
	
	@PutMapping("/alterar/{id}")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
	public ResponseEntity<ApiResponseDTO> alterarUsuario(@Valid @RequestBody UsuarioRequestDTO usuario, @PathVariable Long id) {
		return usuarioService.alterarUsuario(usuario, id);
	}
	
	@DeleteMapping("/remover/{id}")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	public ResponseEntity<ApiResponseDTO> removerUsuario(@PathVariable Long id) {
		return usuarioService.removerUsuario(id);
	}
	
	@PostMapping("/listar")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
	public ResponseEntity<ApiResponseDTO> listarUsuario(@Valid @RequestBody SearchRequestDTO filtros) {
		return usuarioService.listarUsuario(filtros);
	}
}
