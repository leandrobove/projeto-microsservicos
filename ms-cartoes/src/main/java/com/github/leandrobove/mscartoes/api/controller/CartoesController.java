package com.github.leandrobove.mscartoes.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.leandrobove.mscartoes.api.dto.CartaoSaveRequest;
import com.github.leandrobove.mscartoes.domain.model.Cartao;
import com.github.leandrobove.mscartoes.domain.service.CartaoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/cartoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartoesController {
	
	private final CartaoService cartaoService;
	
	@PostMapping
	public ResponseEntity<Cartao> salvar(@RequestBody @Valid CartaoSaveRequest cartaoSaveRequest) {		
		Cartao cartaoSalvo = cartaoService.salvar(cartaoSaveRequest.toModel());
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(cartaoSalvo.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(cartaoSalvo);
	}
	
	@GetMapping(value = "/{cartaoId}")
	public Cartao buscarPorId(@PathVariable Long cartaoId) {
		return cartaoService.buscarPorId(cartaoId);
	}
	
	@GetMapping
	public List<Cartao> getCartoesRendaAte(@RequestParam(value = "renda") Long renda) {
		return cartaoService.getCartoesRendaMenorIgual(renda);
	}
	
}
