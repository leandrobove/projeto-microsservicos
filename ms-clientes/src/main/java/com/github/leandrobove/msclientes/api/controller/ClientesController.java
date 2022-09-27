package com.github.leandrobove.msclientes.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.leandrobove.msclientes.api.dto.request.ClienteSaveRequest;
import com.github.leandrobove.msclientes.domain.model.Cliente;
import com.github.leandrobove.msclientes.domain.repository.ClienteRepository;
import com.github.leandrobove.msclientes.domain.service.ClienteService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@RestController
@RequestMapping(value = "/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientesController {

	private final ClienteService clienteService;
	
	private final ClienteRepository clienteRepository;

	@PostMapping
	public ResponseEntity<Cliente> salvar(@RequestBody @Valid ClienteSaveRequest clienteSaveRequest) {
		Cliente clienteSalvo = clienteService.salvar(clienteSaveRequest.toModel());

		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.query("cpf={cpf}")
				.buildAndExpand(clienteSalvo.getCpf())
				.toUri();

		return ResponseEntity.created(uri).body(clienteSalvo);
	}

	@GetMapping(params = "cpf")
	public Cliente buscarPorCpf(@RequestParam String cpf) {
		return clienteService.buscarPorCpf(cpf);
	}
	
	@GetMapping
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}
}
