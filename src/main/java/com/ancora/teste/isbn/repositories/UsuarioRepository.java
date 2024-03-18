package com.ancora.teste.isbn.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ancora.teste.isbn.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>,
		JpaSpecificationExecutor<Usuario> {
	
	Optional<Usuario> findById(Long id);

}
