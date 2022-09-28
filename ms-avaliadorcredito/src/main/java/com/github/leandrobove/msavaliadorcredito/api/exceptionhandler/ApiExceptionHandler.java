package com.github.leandrobove.msavaliadorcredito.api.exceptionhandler;

import java.time.OffsetDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.github.leandrobove.msavaliadorcredito.api.exceptions.ClienteNaoEncontradoException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. "
			+ "Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.";

	@ExceptionHandler(ClienteNaoEncontradoException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontradaException(ClienteNaoEncontradoException e, WebRequest req) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		String detail = e.getMessage();

		Problem problem = this.createProblemBuilder(status, detail).message(detail).build();

		return this.handleExceptionInternal(e, problem, new HttpHeaders(), status, req);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleUncaught(Exception ex, WebRequest req) {

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;

		log.error(ex.getMessage(), ex);

		Problem problem = this.createProblemBuilder(status, detail).message(detail).build();

		return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, req);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		body = Problem.builder().timestamp(OffsetDateTime.now()).message(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.status(status.value()).build();

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, String detail) {
		return Problem.builder().status(status.value()).message(detail).timestamp(OffsetDateTime.now());
	}

}
