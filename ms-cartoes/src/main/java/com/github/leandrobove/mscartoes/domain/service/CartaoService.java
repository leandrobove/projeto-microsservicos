package com.github.leandrobove.mscartoes.domain.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.leandrobove.mscartoes.domain.exception.CartaoNaoEncontradoException;
import com.github.leandrobove.mscartoes.domain.model.Cartao;
import com.github.leandrobove.mscartoes.domain.repository.CartaoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartaoService {

	private final CartaoRepository cartaoRepository;

	@Transactional
	public Cartao salvar(Cartao cartao) {
		return cartaoRepository.save(cartao);
	}

	public List<Cartao> listar() {
		return cartaoRepository.findAll();
	}

	public Cartao buscarPorId(Long cartaoId) {
		return cartaoRepository.findById(cartaoId).orElseThrow(() -> new CartaoNaoEncontradoException(cartaoId));
	}

	public List<Cartao> getCartoesRendaMenorIgual(Long renda) {
		BigDecimal rendaBigDecimal = BigDecimal.valueOf(renda);

		return cartaoRepository.findByRendaLessThanEqual(rendaBigDecimal);
	}
}
