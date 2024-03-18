package com.ancora.teste.isbn.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ancora.teste.isbn.entities.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
	Optional<Livro> findByIsbn(String isbn);
}
