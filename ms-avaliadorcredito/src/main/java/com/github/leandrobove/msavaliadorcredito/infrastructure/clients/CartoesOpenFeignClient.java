package com.github.leandrobove.msavaliadorcredito.infrastructure.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.leandrobove.msavaliadorcredito.domain.model.CartaoCliente;

@FeignClient(value = "ms-cartoes", path = "/cartoes")
public interface CartoesOpenFeignClient {

	@GetMapping(params = "cpf")
	List<CartaoCliente> listarCartoesPorCpf(@RequestParam(value = "cpf") String cpf);
}
