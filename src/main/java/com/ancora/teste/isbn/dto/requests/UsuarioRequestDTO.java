package com.ancora.teste.isbn.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class UsuarioRequestDTO {
	
	@NotEmpty(message = "Nome não pode ser vazio")
	@NotBlank(message = "Nome não pode estar em branco")
	private String nome;
	
	@NotEmpty(message = "Email não pode ser vazio")
	@NotBlank(message = "Email não pode estar em branco")
	@Email
	private String email;
	
	@NotEmpty(message = "CPF não pode ser vazio")
	@NotBlank(message = "CPF não pode estar em branco")
	private String cpf;
	
}
