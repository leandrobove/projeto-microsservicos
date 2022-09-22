package com.github.leandrobove.mscartoes.api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cartoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartoesController {

	@GetMapping
	public String status() {
		return "ok";
	}
	
}
