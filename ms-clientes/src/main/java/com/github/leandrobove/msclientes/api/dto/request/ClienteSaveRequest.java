package com.github.leandrobove.msclientes.api.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.leandrobove.msclientes.domain.model.Cliente;

import lombok.Data;

@Data
public class ClienteSaveRequest {

	@NotBlank
	private String nome;
	
	@NotBlank
	@CPF
	@Pattern(regexp = "(^\\d{11}$)")
	private String cpf;
	
	@NotNull
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;

	public Cliente toModel() {
		return new Cliente(null, nome, cpf, dataNascimento);
	}

}
