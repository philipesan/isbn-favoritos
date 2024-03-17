package com.ancora.teste.isbn.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="api/v1/usuario")
public class UsuarioController {

	@GetMapping
	public String home() {
		return "Hi";
	}
}
