package com.ancora.teste.isbn.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ancora.teste.isbn.dto.responses.ApiResponseDTO;
import com.ancora.teste.isbn.dto.responses.LivroApiResponseDTO;
import com.ancora.teste.isbn.entities.Livro;
import com.ancora.teste.isbn.mapstruct.LivroMapper;
import com.ancora.teste.isbn.repositories.LivroRepository;
import com.ancora.teste.isbn.services.LivroService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class LivroServiceImpl implements LivroService {


	@Autowired
	LivroRepository livroRepo;
	
	@Autowired 
	RestTemplate restTemplate;
	
	@Autowired
	LivroMapper livroMapper;
	
	private final String ISBN_URI = "https://brasilapi.com.br/api/isbn/v1/";
	
	@Override
	@CachePut(cacheNames = "livros", key = "#isbn")	
	public ResponseEntity<ApiResponseDTO> buscaLivro(String isbn) {
		log.info("Buscando ISBN: " + isbn);
		
		Livro livro = new Livro();
		try {
			livro = this.encontraLivro(isbn);
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
		
		log.info("Livro encontrado, salvando no banco!");
		
		if (livro.getId() == null) {
			try {
				livroRepo.save(livro);
			} catch (Exception e) {
				log.warn("Não foi possivel salvar livro");
				log.error(e.getMessage());
			}
		}
		
		
		return ResponseEntity.status(200).body(ApiResponseDTO.builder()
				.message("Livro Encontrado!")
				.content(livroMapper.toResponseDTO(livro))
				.build());
	}
	
	@Override
	public Livro encontraLivro(String isbn) throws Exception {
		try {
			log.info("Tentando encontrar livro no banco de dados");
			Optional<Livro> livroBanco = this.encontraLivroBanco(isbn);
			if(livroBanco.isPresent()) return livroBanco.get();
			log.info("Tentando buscar livro na API");
			Livro livro = this.encontraLivroApi(isbn);
			return livro;
			
		} catch (JsonProcessingException e) {
			log.warn("Erro ao tratar JSON da Request");
			log.error(e.getMessage());
			
			return null;
			
		} catch (HttpClientErrorException e) {
			log.warn("Erro na API");
			log.error(e.getMessage());
			
			return null;
		} catch (Exception e) {
			log.warn("Erro inexperado na aplicação");
			log.error(e.getMessage());
			
			return null;
		}
		
	}
	
	@CachePut(cacheNames = "livros", key = "#isbn")	
	private Optional<Livro> encontraLivroBanco(String isbn) {
		return livroRepo.findByIsbn(isbn);
	}
	
	@CachePut(cacheNames = "livros", key = "#isbn")	
	private Livro encontraLivroApi(String isbn)  throws JsonMappingException, JsonProcessingException {
		
		LivroApiResponseDTO listaLivros = this.buscaDados(ISBN_URI + isbn);
		
		if(listaLivros == null) return null;
		
		Livro livro = livroMapper.apiToEntity(listaLivros);
		
		livro.setAutores(String.join(", ", listaLivros.getAuthors()));
		
		return livro;
		
		
	}
	
    public String executaHttp(String uri) {
    	return this.restTemplate.getForObject(uri, String.class);
    }
    
	@CachePut(cacheNames = "livrosApi", key = "#isbn")	
	public LivroApiResponseDTO buscaDados(String uri) throws JsonMappingException, JsonProcessingException {

        String resposta = executaHttp(uri);
		ObjectMapper objMap = new ObjectMapper();
		return (!resposta.equals("") ? objMap.readValue(resposta, new TypeReference<LivroApiResponseDTO>(){})
									 : null);
	}

}
