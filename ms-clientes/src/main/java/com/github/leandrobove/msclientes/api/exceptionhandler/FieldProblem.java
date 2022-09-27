package com.github.leandrobove.msclientes.api.exceptionhandler;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FieldProblem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String message;

}
