package com.ancora.teste.isbn.dto.responses;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioResponseDTO {

	private Long id;
	private String nome;
	private String email;
	private String cpf;
	private LocalDateTime criacao;
}
