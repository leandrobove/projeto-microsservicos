package com.github.leandrobove.mscartoes.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

@SequenceGenerator(name = "cartoes", sequenceName = "SEQ_CARTOES", initialValue = 1)
@Entity
public class Cartao {

	@Id
	@GeneratedValue(generator = "cartoes", strategy = GenerationType.SEQUENCE)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Enumerated(EnumType.STRING)
	private BandeiraCartao bandeira;

	private BigDecimal renda;

	@Column(nullable = false)
	private BigDecimal limiteBasico;

}
