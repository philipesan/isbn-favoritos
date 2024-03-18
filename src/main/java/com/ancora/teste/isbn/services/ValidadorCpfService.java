package com.ancora.teste.isbn.services;

import org.springframework.stereotype.Service;

@Service
public interface ValidadorCpfService {
	boolean validarCpf(String cpf);
	String limparCpf(String cpf);
	
}
