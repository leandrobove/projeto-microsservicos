package com.github.leandrobove.msavaliadorcredito.api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/avaliacoes-credito", produces = MediaType.APPLICATION_JSON_VALUE)
public class AvaliadorCreditoController {

	@GetMapping
	public String status() {
		return "ok";
	}
}
