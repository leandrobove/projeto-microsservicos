package com.github.leandrobove.mscartoes.domain.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter

@SequenceGenerator(name = "cliente_cartao", sequenceName = "SEQ_CLIENTE_CARTAO", initialValue = 1)
@Entity
public class ClienteCartao {
	
	@Id
	@GeneratedValue(generator = "cliente_cartao", strategy = GenerationType.SEQUENCE)
	@EqualsAndHashCode.Include
	private Long id;
	
	private String cpf;
	
	@ManyToOne
	@JoinColumn(name = "id_cartao")
	private Cartao cartao;
	
	private BigDecimal limite;

}
