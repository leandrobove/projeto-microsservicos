package com.github.leandrobove.msavaliadorcredito.api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.leandrobove.msavaliadorcredito.domain.model.DadosAvaliacao;
import com.github.leandrobove.msavaliadorcredito.domain.model.RetornoAvaliacaoCliente;
import com.github.leandrobove.msavaliadorcredito.domain.model.SituacaoCliente;
import com.github.leandrobove.msavaliadorcredito.domain.service.AvaliadorCreditoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@RestController
@RequestMapping(value = "/avaliacoes-credito", produces = MediaType.APPLICATION_JSON_VALUE)
public class AvaliadorCreditoController {

	private final AvaliadorCreditoService avaliadorCreditoService;

	@GetMapping(value = "/situacao-cliente", params = "cpf")
	public SituacaoCliente consultaSituacaoCliente(@RequestParam("cpf") String cpf) {
		SituacaoCliente situacaoCliente = avaliadorCreditoService.getSituacaoCliente(cpf);

		return situacaoCliente;
	}

	@PostMapping
	public RetornoAvaliacaoCliente realizarAvaliacao(@RequestBody DadosAvaliacao dadosAvaliacao) {
		return avaliadorCreditoService.realizarAvaliacao(dadosAvaliacao.getCpf(), dadosAvaliacao.getRenda());
	}
}
