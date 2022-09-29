package com.github.leandrobove.msavaliadorcredito.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.github.leandrobove.msavaliadorcredito.api.exceptions.ClienteNaoEncontradoException;
import com.github.leandrobove.msavaliadorcredito.api.exceptions.ErroComunicacaoMicrosservicosException;
import com.github.leandrobove.msavaliadorcredito.domain.exceptions.ErroSolicitacaoEmissaoCartaoException;
import com.github.leandrobove.msavaliadorcredito.domain.model.Cartao;
import com.github.leandrobove.msavaliadorcredito.domain.model.CartaoAprovado;
import com.github.leandrobove.msavaliadorcredito.domain.model.CartaoCliente;
import com.github.leandrobove.msavaliadorcredito.domain.model.Cliente;
import com.github.leandrobove.msavaliadorcredito.domain.model.DadosSolicitacaoEmissaoCartao;
import com.github.leandrobove.msavaliadorcredito.domain.model.ProtocoloSolicitacaoCartao;
import com.github.leandrobove.msavaliadorcredito.domain.model.RetornoAvaliacaoCliente;
import com.github.leandrobove.msavaliadorcredito.domain.model.SituacaoCliente;
import com.github.leandrobove.msavaliadorcredito.infrastructure.clients.CartoesOpenFeignClient;
import com.github.leandrobove.msavaliadorcredito.infrastructure.clients.ClienteOpenFeignClient;
import com.github.leandrobove.msavaliadorcredito.infrastructure.mqueue.EmissaoCartaoProducer;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Service
public class AvaliadorCreditoService {

	private final ClienteOpenFeignClient clientesOpenFeignClient;
	private final CartoesOpenFeignClient cartoesOpenFeignClient;
	private final EmissaoCartaoProducer emissaoCartaoProducer;

	public SituacaoCliente getSituacaoCliente(String cpf) {

		try {
			// Obter dados do cliente - ms-clientes - GET /clientes?cpf={cpf}
			Cliente clienteResponse = clientesOpenFeignClient.buscarPorCpf(cpf);
	
			// Obter cartões do cliente - ms-cartoes - GET /cartoes/?cpf={cpf}
			List<CartaoCliente> cartoesResponse = cartoesOpenFeignClient.listarCartoesPorCpf(cpf);
			
			return SituacaoCliente.builder()
					.cliente(clienteResponse)
					.cartoes(cartoesResponse)
					.build();
		} catch (FeignException.FeignClientException e) {
			if(e.status() == HttpStatus.NOT_FOUND.value()) {
				throw new ClienteNaoEncontradoException(cpf);
			}
			
			throw new ErroComunicacaoMicrosservicosException(e.getMessage(), e.status());
		}
	}

	public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) {
		try {
			// Obter dados do cliente - ms-clientes - GET /clientes?cpf={cpf}
			Cliente clienteResponse = clientesOpenFeignClient.buscarPorCpf(cpf);
			
			// Obter cartões com a renda até a do cliente - ms-cartoes - GET /cartoes?renda={renda}
			List<Cartao> cartoesRendaAteReponse = cartoesOpenFeignClient.getCartoesRendaAte(renda);
			
			List<CartaoAprovado> cartoesAprovados = cartoesRendaAteReponse.stream().map(cartao -> {
				CartaoAprovado cartaoAprovado = new CartaoAprovado();
				
				cartaoAprovado.setCartao(cartao.getNome());
				cartaoAprovado.setBandeira(cartao.getBandeira());
				cartaoAprovado.setLimiteAprovado(calcularLimiteAprovado(clienteResponse.getIdade(), cartao.getLimiteBasico()));
				
				return cartaoAprovado;
			}).collect(Collectors.toList());
			
			return new RetornoAvaliacaoCliente(cartoesAprovados);
			
		} catch (FeignException.FeignClientException e) {
			if(e.status() == HttpStatus.NOT_FOUND.value()) {
				throw new ClienteNaoEncontradoException(cpf);
			}
			
			throw new ErroComunicacaoMicrosservicosException(e.getMessage(), e.status());
		}
	}
	
	public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dadosSolicitacaoEmissaoCartao) {
		try {
			//enviar msg para o broker
			emissaoCartaoProducer.solicitarCartao(dadosSolicitacaoEmissaoCartao);
			
			//retornar protocolo de solicitação
			var protocoloGerado = UUID.randomUUID().toString();
			
			return new ProtocoloSolicitacaoCartao(protocoloGerado);
			
		} catch (Exception e) {
			throw new ErroSolicitacaoEmissaoCartaoException(e.getMessage());
		}
	}
	
	private static BigDecimal calcularLimiteAprovado(Integer idadeCliente, BigDecimal limiteBasico) {
		BigDecimal idade = BigDecimal.valueOf(idadeCliente);

		var fator = idade.divide(BigDecimal.valueOf(10));

		return fator.multiply(limiteBasico);
	}
}
