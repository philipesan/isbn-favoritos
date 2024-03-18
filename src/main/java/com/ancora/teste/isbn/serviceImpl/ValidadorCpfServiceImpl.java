package com.ancora.teste.isbn.serviceImpl;

import org.springframework.stereotype.Component;

import com.ancora.teste.isbn.services.ValidadorCpfService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ValidadorCpfServiceImpl implements ValidadorCpfService {

	
	@Override
	public boolean validarCpf(String cpf) {
		cpf = this.limparCpf(cpf);
        if (cpf == null || cpf.length() != 11 || !cpf.chars().allMatch(Character::isDigit)) {
            return false;
        }
        
        
        return validaDigitoCpf(cpf, 10) && validaDigitoCpf(cpf, 11);
	}


	@Override
	public String limparCpf(String cpf) {
		return cpf.replaceAll("[^0-9]", "");
	}

    private boolean validaDigitoCpf(String cpf, int posicao) {
        Integer soma = 0;
        Integer indice = Integer.valueOf(posicao - 1);
        Integer posicaoInicial = Integer.valueOf(posicao);
        
        for (int i = 0; i < indice; i++) {
        	soma += Integer.parseInt(cpf.substring(i, i + 1)) * posicao--;
        }
        
        soma = (soma * 10) % 11;
        soma = soma == 10 ? 0 : soma;
        Integer digito = Integer.parseInt(cpf.substring(indice, posicaoInicial));
        return digito == soma;
    }
}
