package com.github.leandrobove.msavaliadorcredito.domain.service;

import org.springframework.stereotype.Service;

import com.github.leandrobove.msavaliadorcredito.domain.model.Cliente;
import com.github.leandrobove.msavaliadorcredito.domain.model.SituacaoCliente;
import com.github.leandrobove.msavaliadorcredito.infrastructure.clients.ClienteOpenFeignClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Service
public class AvaliadorCreditoService {

	private final ClienteOpenFeignClient clientesClient;

	public SituacaoCliente getSituacaoCliente(String cpf) {

		// Obter dados do cliente - ms-clientes - GET /clientes?cpf={cpf}
		Cliente cliente = clientesClient.buscarPorCpf(cpf);

		// Obter cartões do cliente - ms-cartoes - GET /cartoes/?cpf={cpf}

		return SituacaoCliente.builder()
				.cliente(cliente)
				.build();
	}

}
