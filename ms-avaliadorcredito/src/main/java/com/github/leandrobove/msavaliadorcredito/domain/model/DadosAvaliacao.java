package com.github.leandrobove.msavaliadorcredito.domain.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;

@Data
public class DadosAvaliacao {

	@NotBlank
	@CPF
	@Pattern(regexp = "(^\\d{11}$)", message = "deve conter números somente números")
	private String cpf;

	@NotNull
	private Long renda;
}
