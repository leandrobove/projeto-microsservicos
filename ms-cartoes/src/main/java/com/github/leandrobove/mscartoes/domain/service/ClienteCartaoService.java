package com.github.leandrobove.mscartoes.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.leandrobove.mscartoes.domain.model.ClienteCartao;
import com.github.leandrobove.mscartoes.domain.repository.ClienteCartaoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClienteCartaoService {

	private final ClienteCartaoRepository clienteCartaoRepository;

	public List<ClienteCartao> listarCartoesPorCpf(String cpf) {
		return clienteCartaoRepository.findByCpf(cpf);
	}
}
