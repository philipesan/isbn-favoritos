package com.ancora.teste.isbn.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ancora.teste.isbn.dto.requests.SearchRequestDTO;
import com.ancora.teste.isbn.dto.requests.UsuarioRequestDTO;
import com.ancora.teste.isbn.dto.responses.ApiResponseDTO;

@Service
public interface UsuarioService {
	ResponseEntity<ApiResponseDTO> createUsuario(UsuarioRequestDTO usuario);
	ResponseEntity<ApiResponseDTO> listarUsuario(SearchRequestDTO filtros);
}
