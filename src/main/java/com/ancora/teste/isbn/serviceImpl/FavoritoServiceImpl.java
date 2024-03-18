package com.ancora.teste.isbn.serviceImpl;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ancora.teste.isbn.dto.responses.ApiResponseDTO;
import com.ancora.teste.isbn.dto.responses.UsuarioResponseDTO;
import com.ancora.teste.isbn.entities.Livro;
import com.ancora.teste.isbn.entities.Usuario;
import com.ancora.teste.isbn.mapstruct.LivroMapper;
import com.ancora.teste.isbn.mapstruct.UsuarioMapper;
import com.ancora.teste.isbn.repositories.LivroRepository;
import com.ancora.teste.isbn.repositories.UsuarioRepository;
import com.ancora.teste.isbn.services.FavoritoService;
import com.ancora.teste.isbn.services.LivroService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FavoritoServiceImpl implements FavoritoService {
	
	@Autowired
	UsuarioRepository usuarioRepo;
	
	@Autowired
	LivroRepository livroRepo;
	
	@Autowired
	LivroService livroService;
	
	@Autowired
	UsuarioMapper usuarioMapper;
	
	@Autowired
	LivroMapper livroMapper;
	

	
	@Override
    @Cacheable(cacheNames = "usuario")	
	public ResponseEntity<ApiResponseDTO> adicionarFavorito(Long id, String isbn) {
	
		log.info("Adicionando livro: " + isbn + " ao usuário: " + id);
		Livro livro = new Livro();
		Usuario usuario = new Usuario();
		
		try {
			Optional<Usuario> usuarioOpt = usuarioRepo.findById(id);
			
			if(usuarioOpt.isEmpty()) {
				return ResponseEntity.status(400).body(ApiResponseDTO.builder()
						.message("Usuário inexistente")
						.build());
				
			}
			
			usuario = usuarioOpt.get();
			
		} catch (Exception e) {
			log.warn("Erro inexperado ao adicionar favorito");
			log.error(e.getMessage());
			return ResponseEntity.status(503).body(ApiResponseDTO.builder()
					.message("Erro inexperado")
					.content(e.getMessage())
					.build());			
		}
		
		try {
			
			livro = livroService.encontraLivro(isbn);
			
		} catch (Exception e) {
			
			return ResponseEntity.status(503).body(ApiResponseDTO.builder()
					.message("Erro ao buscar livro!")
					.content(e.getMessage())
					.build());
		}
		
		if(livro == null) {
			
			log.info("ISBN não encontrado na API nem no banco");
			return ResponseEntity.status(404).body(ApiResponseDTO.builder()
				.message("Nenhum Livro encontrado com o ISBN")
				.build());
		}
		
		usuario.getFavoritos().add(livro);
		
		if(livro.getFavoritados() == null) {
			livro.setFavoritados(Set.of(usuario)); 
		} else {
			livro.getFavoritados().add(usuario);
		}
		
		try {
			livro = livroRepo.save(livro);
			usuario = usuarioRepo.save(usuario);
			
		} catch (Exception e) {
			log.warn("Erro inexperado ao adicionar favorito");
			log.error(e.getMessage());
			return ResponseEntity.status(503).body(ApiResponseDTO.builder()
					.message("Erro inexperado")
					.content(e.getMessage())
					.build());
		}
		
		UsuarioResponseDTO usuarioResponse = usuarioMapper.toResponseDTO(usuario);
		usuarioResponse.setFavoritos(usuario
				.getFavoritos()
				.stream()
				.map(livroMapper::toResponseDTO)
				.collect(Collectors.toSet()));
		
		return ResponseEntity.status(200).body(ApiResponseDTO.builder()
				.message("Favorito adicionado com sucesso")
				.content(usuarioResponse)
				.build());

	}
	
	@Override
	public ResponseEntity<ApiResponseDTO> removerFavorito(Long id, String isbn) {
		log.info("Adicionando livro: " + isbn + " ao usuário: " + id);
		Livro livro = new Livro();
		Usuario usuario = new Usuario();
		
		try {
			Optional<Usuario> usuarioOpt = usuarioRepo.findById(id);
			
			if(usuarioOpt.isEmpty()) {
				return ResponseEntity.status(400).body(ApiResponseDTO.builder()
						.message("Usuário inexistente")
						.build());
				
			}
			
			usuario = usuarioOpt.get();
			
			Optional<Livro> livroOpt = livroRepo.findByIsbn(isbn);
			
			if(usuarioOpt.isEmpty()) {
				return ResponseEntity.status(400).body(ApiResponseDTO.builder()
						.message("Livro inexistente")
						.build());
				
			}
			
			livro = livroOpt.get();
			
			usuario.getFavoritos().remove(livro);
			livro.getFavoritados().remove(usuario);
			
			livro = livroRepo.save(livro);
			usuario = usuarioRepo.save(usuario);
			
		} catch (Exception e) {
			log.warn("Erro inexperado ao Remover favorito");
			log.error(e.getMessage());
			return ResponseEntity.status(503).body(ApiResponseDTO.builder()
					.message("Erro inexperado")
					.content(e.getMessage())
					.build());			
		}		
			
		UsuarioResponseDTO usuarioResponse = usuarioMapper.toResponseDTO(usuario);
		usuarioResponse.setFavoritos(usuario
				.getFavoritos()
				.stream()
				.map(livroMapper::toResponseDTO)
				.collect(Collectors.toSet()));
		
		return ResponseEntity.status(200).body(ApiResponseDTO.builder()
				.message("Favorito Removido com sucesso")
				.content(usuarioResponse)
				.build());	

	}


}
