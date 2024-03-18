package com.ancora.teste.isbn.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ancora.teste.isbn.dto.responses.LivroApiResponseDTO;
import com.ancora.teste.isbn.dto.responses.LivroResponseDTO;
import com.ancora.teste.isbn.entities.Livro;

@Mapper(componentModel="spring")
public interface LivroMapper {
	LivroMapper INSTANCE = Mappers.getMapper(LivroMapper.class);
	
	@Mapping(target="isbn", source="isbn")
	@Mapping(target="titulo", source="title")
	@Mapping(target="subtitulo", source="subtitle")
	@Mapping(target="editora", source="publisher")
	@Mapping(target="ano", source="year")
	Livro apiToEntity(LivroApiResponseDTO livro);
	
	@Mapping(target="isbn", source="isbn")
	@Mapping(target="titulo", source="titulo")
	@Mapping(target="subtitulo", source="subtitulo")
	@Mapping(target="editora", source="editora")
	@Mapping(target="ano", source="ano")
	@Mapping(target="autores", source="autores")
	LivroResponseDTO toResponseDTO(Livro livro);
}
