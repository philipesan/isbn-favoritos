package com.ancora.teste.isbn.entities;

import java.time.LocalDateTime;

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
@Table(name = "tb_usuario")
public class Usuario {
	@Id
	@GeneratedValue
	private Long id;
	
	private String nome;
	
	@Column(unique=true)
	private String email;
	private String cpf;
	private LocalDateTime criacao;
	
}
