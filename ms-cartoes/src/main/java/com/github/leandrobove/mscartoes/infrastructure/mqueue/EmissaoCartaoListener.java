package com.github.leandrobove.mscartoes.infrastructure.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.github.leandrobove.mscartoes.domain.model.DadosSolicitacaoEmissaoCartao;

@Component
public class EmissaoCartaoListener {

	@RabbitListener(queues = "${mq.queues.emissao-cartoes}")
	public void receberSolicitacaoEmissao(@Payload DadosSolicitacaoEmissaoCartao dadosSolicitacaoEmissaoCartao) {
		System.out.println(dadosSolicitacaoEmissaoCartao);
	}

}
