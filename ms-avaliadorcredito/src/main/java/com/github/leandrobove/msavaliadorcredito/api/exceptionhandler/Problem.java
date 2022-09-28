package com.github.leandrobove.msavaliadorcredito.api.exceptionhandler;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)

@Getter
@Builder
public class Problem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer status;
		
	private String message;
	
	private List<FieldProblem> fields;
	
	private OffsetDateTime timestamp;

}
