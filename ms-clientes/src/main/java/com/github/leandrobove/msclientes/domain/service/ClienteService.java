package com.github.leandrobove.msclientes.domain.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.leandrobove.msclientes.domain.exception.ClienteNaoEncontradoException;
import com.github.leandrobove.msclientes.domain.exception.NegocioException;
import com.github.leandrobove.msclientes.domain.model.Cliente;
import com.github.leandrobove.msclientes.domain.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClienteService {

	private final ClienteRepository clienteRepository;

	@Transactional
	public Cliente salvar(Cliente cliente) {
		try {
			Cliente clienteSalvo = clienteRepository.save(cliente);
			clienteRepository.flush();

			return clienteSalvo;
		} catch (DataIntegrityViolationException e) {
			throw new NegocioException(String.format("Já possui um cliente cadastrado com o cpf %s", cliente.getCpf()));
		}
	}

	public Cliente buscarPorCpf(String cpf) {
		return clienteRepository.findByCpf(cpf).orElseThrow(() -> new ClienteNaoEncontradoException(cpf));
	}
}
