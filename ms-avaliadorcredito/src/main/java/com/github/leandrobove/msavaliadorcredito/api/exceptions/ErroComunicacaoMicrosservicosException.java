package com.github.leandrobove.msavaliadorcredito.api.exceptions;

import lombok.Getter;

@Getter
public class ErroComunicacaoMicrosservicosException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Integer statusCode;
	
	public ErroComunicacaoMicrosservicosException(String msg, Integer statusCode) {
		super(msg);
		
		this.statusCode = statusCode;
	}

}
