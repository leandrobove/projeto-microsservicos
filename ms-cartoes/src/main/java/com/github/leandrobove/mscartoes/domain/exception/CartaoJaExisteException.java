package com.github.leandrobove.mscartoes.domain.exception;

public class CartaoJaExisteException extends NegocioException {
	
	private static final long serialVersionUID = 1L;

	private static final String MSG_CARTAO_JA_EXISTENTE = "O cartão de id %d já está associado ao cpf %s";

	public CartaoJaExisteException(Long cartaoId, String cpf) {
		super(String.format(MSG_CARTAO_JA_EXISTENTE, cartaoId, cpf));
	}

}
