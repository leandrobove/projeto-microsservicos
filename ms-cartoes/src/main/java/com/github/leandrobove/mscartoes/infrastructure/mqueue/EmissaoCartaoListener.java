package com.github.leandrobove.mscartoes.infrastructure.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.github.leandrobove.mscartoes.domain.exception.CartaoNaoEncontradoException;
import com.github.leandrobove.mscartoes.domain.model.Cartao;
import com.github.leandrobove.mscartoes.domain.model.ClienteCartao;
import com.github.leandrobove.mscartoes.domain.model.DadosSolicitacaoEmissaoCartao;
import com.github.leandrobove.mscartoes.domain.repository.CartaoRepository;
import com.github.leandrobove.mscartoes.domain.service.ClienteCartaoService;

@Component
public class EmissaoCartaoListener {
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private ClienteCartaoService clienteCartaoService;

	@RabbitListener(queues = "${mq.queues.emissao-cartoes}")
	public void receberSolicitacaoEmissao(@Payload DadosSolicitacaoEmissaoCartao dadosSolicitacaoEmissaoCartao) {
		System.out.println(dadosSolicitacaoEmissaoCartao);
		
		Long idCartao = dadosSolicitacaoEmissaoCartao.getIdCartao();
		
		Cartao cartao = cartaoRepository.findById(idCartao).orElseThrow(() -> new CartaoNaoEncontradoException(idCartao));
		
		ClienteCartao clienteCartao = ClienteCartao.builder()
			.cartao(cartao)
			.cpf(dadosSolicitacaoEmissaoCartao.getCpf())
			.limite(dadosSolicitacaoEmissaoCartao.getLimiteLiberado())
			.build();
		
		clienteCartaoService.salvar(clienteCartao);
	}

}
