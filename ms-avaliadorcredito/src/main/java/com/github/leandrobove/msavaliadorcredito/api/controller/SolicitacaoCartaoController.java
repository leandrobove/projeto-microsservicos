package com.github.leandrobove.msavaliadorcredito.api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.leandrobove.msavaliadorcredito.domain.model.DadosSolicitacaoEmissaoCartao;
import com.github.leandrobove.msavaliadorcredito.domain.model.ProtocoloSolicitacaoCartao;
import com.github.leandrobove.msavaliadorcredito.domain.service.AvaliadorCreditoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@RestController
@RequestMapping(value = "/solicitacoes-cartao", produces = MediaType.APPLICATION_JSON_VALUE)
public class SolicitacaoCartaoController {

	private final AvaliadorCreditoService avaliadorCreditoService;

	@PostMapping
	public ProtocoloSolicitacaoCartao solicitarCartao(
			@RequestBody DadosSolicitacaoEmissaoCartao dadosSolicitacaoEmissaoCartao) {
		return avaliadorCreditoService.solicitarEmissaoCartao(dadosSolicitacaoEmissaoCartao);
	}
}
