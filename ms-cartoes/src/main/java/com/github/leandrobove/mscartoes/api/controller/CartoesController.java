package com.github.leandrobove.mscartoes.api.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import com.github.leandrobove.mscartoes.api.dto.request.CartaoSaveRequest;
import com.github.leandrobove.mscartoes.api.dto.response.CartoesPorClienteResponse;
import com.github.leandrobove.mscartoes.domain.model.Cartao;
import com.github.leandrobove.mscartoes.domain.model.ClienteCartao;
import com.github.leandrobove.mscartoes.domain.service.CartaoService;
import com.github.leandrobove.mscartoes.domain.service.ClienteCartaoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/cartoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartoesController {
	
	private final CartaoService cartaoService;
	
	private final ClienteCartaoService clienteCartaoService;
	
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
	
	@GetMapping(params = "renda")
	public List<Cartao> getCartoesRendaAte(@RequestParam(value = "renda") Long renda) {
		return cartaoService.getCartoesRendaMenorIgual(renda);
	}
	
	@GetMapping
	public List<Cartao> listar() {
		return cartaoService.listar();
	}
	
	@GetMapping(params = "cpf")
	public List<CartoesPorClienteResponse> listarCartoesPorCpf(@RequestParam(value = "cpf") String cpf) {
		List<ClienteCartao> listarCartoesPorCpf = clienteCartaoService.listarCartoesPorCpf(cpf);
		
		List<CartoesPorClienteResponse> cartoesPorClienteResponse = listarCartoesPorCpf.stream()
				.map(CartoesPorClienteResponse::fromModel).collect(Collectors.toList());
		
		return cartoesPorClienteResponse;
	}
}
