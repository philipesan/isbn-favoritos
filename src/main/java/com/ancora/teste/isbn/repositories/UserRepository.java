package com.ancora.teste.isbn.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ancora.teste.isbn.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUserName(String userName);
}
