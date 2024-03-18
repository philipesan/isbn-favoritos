package com.ancora.teste.isbn.serviceImpl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ancora.teste.isbn.dto.requests.SearchRequestDTO;
import com.ancora.teste.isbn.dto.requests.UsuarioRequestDTO;
import com.ancora.teste.isbn.dto.responses.ApiResponseDTO;
import com.ancora.teste.isbn.dto.responses.UsuarioResponseDTO;
import com.ancora.teste.isbn.entities.Usuario;
import com.ancora.teste.isbn.mapstruct.UsuarioMapper;
import com.ancora.teste.isbn.repositories.UsuarioRepository;
import com.ancora.teste.isbn.services.UsuarioService;
import com.ancora.teste.isbn.services.ValidadorCpfService;
import com.ancora.teste.isbn.specification.SearchSpecification;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired ValidadorCpfService validadorCpf;
	
	@Autowired UsuarioRepository usuarioRepo;
	
	@Autowired UsuarioMapper usuarioMapper;
	
	@Override
	public ResponseEntity<ApiResponseDTO> createUsuario(UsuarioRequestDTO usuario) {
		
		log.info("Registrando Usuario: "+ usuario.getNome());

		if (validadorCpf.validarCpf(usuario.getCpf()) == false) {
			log.warn("!!!CPF INVALIDO!!!");

			return ResponseEntity.status(422).body(ApiResponseDTO.builder()
					.message("CPF Invalido")
					.build());
		}
		
		Usuario novoUsuario = usuarioMapper.toEntity(usuario);
		novoUsuario.setCriacao(LocalDateTime.now());
		novoUsuario.setFavoritos(Set.of());
		
		try {
			novoUsuario = usuarioRepo.save(novoUsuario);
			log.info("Sucesso na Criacao do usuario");

			return ResponseEntity.status(201).body(ApiResponseDTO.builder()
					.message("Usuario Criado com Sucesso!")
					.content(usuarioMapper.toResponseDTO(novoUsuario))
					.build());

		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			log.warn("Violacao de Constraint na tabela usuario");
			log.error(e.getMessage());
			return ResponseEntity.status(422).body(ApiResponseDTO.builder()
					.message("Usuário já cadastrado")
					.content(e.getMessage())
					.build());
		} catch (Exception e) {
			log.warn("Erro inexperado na criação do usuário");
			log.error(e.getMessage());
			return ResponseEntity.status(503).body(ApiResponseDTO.builder()
					.message("Erro inexperado")
					.content(e.getMessage())
					.build());
		}

	}

	@Override
    @Cacheable(cacheNames = "usuario")	
	public ResponseEntity<ApiResponseDTO> listarUsuario(SearchRequestDTO filtros) {
		
        SearchSpecification<Usuario> specification = new SearchSpecification<>(filtros);
        Pageable pageable = SearchSpecification.getPageable(filtros.getPage(), filtros.getSize());
        Page<UsuarioResponseDTO> pessoas = usuarioRepo.findAll(specification, pageable).map(usuarioMapper::toResponseDTO);
        
		return ResponseEntity.status(200).body(ApiResponseDTO.builder()
				.message("Resultados da consulta:")
				.content(pessoas)
				.build());
	}

	@Override
    @CachePut(cacheNames = "usuario")	
	public ResponseEntity<ApiResponseDTO> alterarUsuario(UsuarioRequestDTO usuario, Long id) {
		
		log.info("Alterando usuário: " + usuario.getNome());
		Usuario usuarioNovo;
		
		if (validadorCpf.validarCpf(usuario.getCpf()) == false) {
			log.warn("!!!CPF INVALIDO!!!");

			return ResponseEntity.status(422).body(ApiResponseDTO.builder()
					.message("CPF Invalido")
					.build());
		}		
		
		try { 
			Optional<Usuario> usuarioConsulta = usuarioRepo.findById(id);
			if(usuarioConsulta.isEmpty()) {
				log.warn("Usuário inexistente, enviando para cadastro");
				return this.createUsuario(usuario);
			}
			
			
		} catch (Exception e) {
			log.warn("Erro inexperado na consulta do usuário");
			log.error(e.getMessage());
			return ResponseEntity.status(503).body(ApiResponseDTO.builder()
					.message("Erro inexperado")
					.content(e.getMessage())
					.build());
		}
		
		usuarioNovo = usuarioMapper.toEntity(usuario);
		usuarioNovo.setId(id);
		try {
			usuarioNovo = usuarioRepo.save(usuarioNovo);
			log.info("Sucesso na Alteração do usuario");

			return ResponseEntity.status(200).body(ApiResponseDTO.builder()
					.message("Usuario Alterado com Sucesso!")
					.content(usuarioMapper.toResponseDTO(usuarioNovo))
					.build());

		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			log.warn("Violacao de Constraint na tabela usuario");
			log.error(e.getMessage());
			return ResponseEntity.status(422).body(ApiResponseDTO.builder()
					.message("Usuário já cadastrado")
					.content(e.getMessage())
					.build());
		} catch (Exception e) {
			log.warn("Erro inexperado na criação do usuário");
			log.error(e.getMessage());
			return ResponseEntity.status(503).body(ApiResponseDTO.builder()
					.message("Erro inexperado")
					.content(e.getMessage())
					.build());
		}

	}

	@Override
    @CacheEvict(cacheNames = "usuario")	
	public ResponseEntity<ApiResponseDTO> removerUsuario(Long id) {
		
		try {
			Optional<Usuario> usuarioOpt = usuarioRepo.findById(id);
			if(usuarioOpt.isEmpty()) {
				return ResponseEntity.status(400).body(ApiResponseDTO.builder()
						.message("Usuário inexistente")
						.build());
				
			}
			
			usuarioRepo.delete(usuarioOpt.get());
			
			return ResponseEntity.status(200).body(ApiResponseDTO.builder()
					.message("Usuário excluido com sucesso")
					.build());
			
		} catch (Exception e) {
			log.warn("Erro inexperado na excluir usuário");
			log.error(e.getMessage());
			return ResponseEntity.status(503).body(ApiResponseDTO.builder()
					.message("Erro inexperado")
					.content(e.getMessage())
					.build());			
		}
		
		
	}

}
