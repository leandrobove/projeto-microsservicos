package com.github.leandrobove.mscartoes.domain.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.leandrobove.mscartoes.domain.exception.CartaoJaExisteException;
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

	@Transactional
	public ClienteCartao salvar(ClienteCartao clienteCartao) {
		try {
			return clienteCartaoRepository.save(clienteCartao);
		} catch (DataIntegrityViolationException e) {
			//lança exceção quando o cartão já está associado a este cliente
			throw new CartaoJaExisteException(clienteCartao.getCartao().getId(), clienteCartao.getCpf());
		}
	}
}
