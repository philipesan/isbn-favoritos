package com.ancora.teste.isbn.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

@Entity
@Table(name = "tb_livros")
public class Livro {

	@Id
	@GeneratedValue
	private Long id;
	@Column(unique=true)
	private Long isbn;
	private String titulo;
	private String subtitulo;
	private String autores;
	private String editora;
	private Integer ano;

}
