package com.github.leandrobove.msavaliadorcredito.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.leandrobove.msavaliadorcredito.domain.model.CartaoCliente;
import com.github.leandrobove.msavaliadorcredito.domain.model.Cliente;
import com.github.leandrobove.msavaliadorcredito.domain.model.SituacaoCliente;
import com.github.leandrobove.msavaliadorcredito.infrastructure.clients.CartoesOpenFeignClient;
import com.github.leandrobove.msavaliadorcredito.infrastructure.clients.ClienteOpenFeignClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Service
public class AvaliadorCreditoService {

	private final ClienteOpenFeignClient clientesOpenFeignClient;
	private final CartoesOpenFeignClient cartoesOpenFeignClient;

	public SituacaoCliente getSituacaoCliente(String cpf) {

		// Obter dados do cliente - ms-clientes - GET /clientes?cpf={cpf}
		Cliente clienteResponse = clientesOpenFeignClient.buscarPorCpf(cpf);

		// Obter cartões do cliente - ms-cartoes - GET /cartoes/?cpf={cpf}
		List<CartaoCliente> cartoesResponse = cartoesOpenFeignClient.listarCartoesPorCpf(cpf);
		
		return SituacaoCliente.builder()
				.cliente(clienteResponse)
				.cartoes(cartoesResponse)
				.build();
	}

}