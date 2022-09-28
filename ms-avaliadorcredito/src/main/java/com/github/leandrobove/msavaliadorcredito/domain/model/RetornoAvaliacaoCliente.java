package com.github.leandrobove.msavaliadorcredito.domain.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RetornoAvaliacaoCliente {

	private List<CartaoAprovado> cartoes = new ArrayList<>();
}
