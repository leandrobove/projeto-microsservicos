package com.github.leandrobove.msavaliadorcredito.infrastructure.mqueue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.leandrobove.msavaliadorcredito.domain.model.DadosSolicitacaoEmissaoCartao;

@Component
public class EmissaoCartaoProducer {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private Queue queueEmissaoCartoes;

	public void solicitarCartao(DadosSolicitacaoEmissaoCartao dadosSolicitacaoEmissaoCartao) {
		rabbitTemplate.convertAndSend(queueEmissaoCartoes.getName(), dadosSolicitacaoEmissaoCartao);
	}

}
