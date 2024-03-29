package com.ancora.teste.isbn.dto.responses;

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
public class LivroResponseDTO {

		private String isbn;
		private String titulo;
		private String subtitulo;
		private String autores;
		private String editora;
		private Integer ano;

}
