package com.github.leandrobove.mscartoes.domain.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DadosSolicitacaoEmissaoCartao {

	private Long idCartao;

	private String cpf;

	private String enderecoEntrega;

	private BigDecimal limiteLiberado;
}
