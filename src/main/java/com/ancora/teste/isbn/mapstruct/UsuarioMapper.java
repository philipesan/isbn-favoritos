package com.ancora.teste.isbn.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ancora.teste.isbn.dto.requests.UsuarioRequestDTO;
import com.ancora.teste.isbn.dto.responses.UsuarioResponseDTO;
import com.ancora.teste.isbn.entities.Usuario;


@Mapper(componentModel="spring")
public interface UsuarioMapper {
	
	UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);
	
	@Mapping(target="nome", source="nome")
	@Mapping(target="email", source="email")
	@Mapping(target="cpf", source="cpf")
	Usuario toEntity(UsuarioRequestDTO usuario);
	
	@Mapping(target="id", source="id")
	@Mapping(target="nome", source="nome")
	@Mapping(target="email", source="email")
	@Mapping(target="cpf", source="cpf")
	UsuarioResponseDTO toResponseDTO(Usuario usuario);

}
