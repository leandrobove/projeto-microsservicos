package com.github.leandrobove.msclientes.domain.exception;

public class ClienteNaoEncontradoException extends NegocioException {

	private static final long serialVersionUID = 1L;

	private static final String MSG_CLIENTE_NAO_ENCONTRADO = "Cliente não encontrado com o cpf %s";

	public ClienteNaoEncontradoException(String cpf) {
		super(String.format(MSG_CLIENTE_NAO_ENCONTRADO, cpf));
	}

}
