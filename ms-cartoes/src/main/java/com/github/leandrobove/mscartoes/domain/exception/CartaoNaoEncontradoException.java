package com.github.leandrobove.mscartoes.domain.exception;

public class CartaoNaoEncontradoException extends NegocioException {

	private static final long serialVersionUID = 1L;

	private static final String MSG_CARTAO_NAO_ENCONTRADO = "Cartão não encontrado com o id %d";

	public CartaoNaoEncontradoException(Long cartaoId) {
		super(String.format(MSG_CARTAO_NAO_ENCONTRADO, cartaoId));
	}

}
