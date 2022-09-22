package com.github.leandrobove.mscartoes.api.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.github.leandrobove.mscartoes.domain.model.BandeiraCartao;
import com.github.leandrobove.mscartoes.domain.model.Cartao;

import lombok.Data;

@Data
public class CartaoSaveRequest {

	@NotBlank
	private String nome;

	@NotNull
	private BandeiraCartao bandeira;

	private BigDecimal renda;

	@NotNull
	private BigDecimal limite;

	public Cartao toModel() {
		return new Cartao(null, nome, bandeira, renda, limite);
	}
}
