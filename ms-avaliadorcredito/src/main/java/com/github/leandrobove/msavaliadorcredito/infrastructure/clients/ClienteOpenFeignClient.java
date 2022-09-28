package com.github.leandrobove.msavaliadorcredito.infrastructure.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.leandrobove.msavaliadorcredito.domain.model.Cliente;

@FeignClient(value = "ms-clientes", path = "/clientes")
public interface ClienteOpenFeignClient {

	@GetMapping(params = "cpf")
	Cliente buscarPorCpf(@RequestParam String cpf);

}
