package com.github.leandrobove.msavaliadorcredito.api.exceptions;

public class ClienteNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final String MSG_CLIENTE_NAO_ENCONTRADO = "Cliente não encontrado com o cpf %s";

	public ClienteNaoEncontradoException(String cpf) {
		super(String.format(MSG_CLIENTE_NAO_ENCONTRADO, cpf));
	}

}
