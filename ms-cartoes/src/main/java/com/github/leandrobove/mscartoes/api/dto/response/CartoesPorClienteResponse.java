package com.github.leandrobove.mscartoes.api.dto.response;

import java.math.BigDecimal;

import com.github.leandrobove.mscartoes.domain.model.ClienteCartao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartoesPorClienteResponse {

	private String nome;
	private String bandeira;
	private BigDecimal limiteLiberado;

	public static CartoesPorClienteResponse fromModel(ClienteCartao clienteCartao) {
		return new CartoesPorClienteResponse(
				clienteCartao.getCartao().getNome(),
				clienteCartao.getCartao().getBandeira().toString(),
				clienteCartao.getLimite()
		);
	}
}
