package com.github.leandrobove.msavaliadorcredito.domain.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class SituacaoCliente {

	private Cliente cliente;
	
	private List<CartaoCliente> cartoes;
}
