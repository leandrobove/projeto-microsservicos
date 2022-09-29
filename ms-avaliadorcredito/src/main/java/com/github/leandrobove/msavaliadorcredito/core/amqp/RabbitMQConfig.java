package com.github.leandrobove.msavaliadorcredito.core.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Value("${mq.queues.emissao-cartoes}")
	private String queueEmissaoCartoes;

	@Bean
	public Queue queueEmissaoCartoes() {
		return new Queue(queueEmissaoCartoes, true);
	}

	@Bean
	public Jackson2JsonMessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
