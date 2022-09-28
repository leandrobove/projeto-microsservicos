package com.github.leandrobove.msclientes.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.github.leandrobove.msclientes.domain.exception.ClienteNaoEncontradoException;
import com.github.leandrobove.msclientes.domain.exception.NegocioException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;

	private static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. "
			+ "Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.";

	@ExceptionHandler(ClienteNaoEncontradoException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontradaException(ClienteNaoEncontradoException e, WebRequest req) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		String detail = e.getMessage();

		Problem problem = this.createProblemBuilder(status, detail).message(detail).build();

		return this.handleExceptionInternal(e, problem, new HttpHeaders(), status, req);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioException(NegocioException e, WebRequest req) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		String detail = e.getMessage();

		Problem problem = this.createProblemBuilder(status, detail).message(detail).build();

		return this.handleExceptionInternal(e, problem, new HttpHeaders(), status, req);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return this.handleValidationInternal(ex, headers, status, request, null);
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
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return this.handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = Problem.builder().timestamp(OffsetDateTime.now()).message(MSG_ERRO_GENERICA_USUARIO_FINAL)
					.status(status.value()).build();
		} else if (body instanceof String) {
			body = Problem.builder().timestamp(OffsetDateTime.now()).message(MSG_ERRO_GENERICA_USUARIO_FINAL)
					.status(status.value()).build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
		
	private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers, HttpStatus status,
			WebRequest request, BindingResult bindingResult) {

		// Mensagem personalizada com mais de um campo
		List<FieldError> campos = bindingResult.getFieldErrors();
		
		List<FieldProblem> problemFields = campos.stream()
				.map( (fieldError) -> {
					
					String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
					
					return FieldProblem.builder()
						.name(fieldError.getField())
						.message(message)
						.build();
				})
				.collect(Collectors.toList());

		String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
		
		Problem problem = createProblemBuilder(status, detail).fields(problemFields).message(detail).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, String detail) {
		return Problem.builder().status(status.value()).message(detail).timestamp(OffsetDateTime.now());
	}

}
