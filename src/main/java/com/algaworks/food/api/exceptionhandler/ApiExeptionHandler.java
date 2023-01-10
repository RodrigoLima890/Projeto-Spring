package com.algaworks.food.api.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.food.domain.exception.EntidadeEmUsoException;
import com.algaworks.food.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.food.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExeptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		String details = ex.getMessage();
		TypeErro typeError = TypeErro.ENTIDADE_NAO_ENCONTRADA;
		
		Error error = createErrorBuilder(status, typeError, details).build();
		
		return handleExceptionInternal(ex, error, new HttpHeaders()
				,status, request);	
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request){
		HttpStatus status = HttpStatus.CONFLICT;
		String details = ex.getMessage();
		TypeErro typeError = TypeErro.ENTIDADE_EM_USO;
		Error error = createErrorBuilder(status, typeError, details).build();
		
		return handleExceptionInternal(ex, error, new HttpHeaders()
				,status, request);	
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		String details = ex.getMessage();
		TypeErro typeErro = TypeErro.ERROR_DE_NEGOCIO;
		Error error = createErrorBuilder(status, typeErro, details).build();
		return handleExceptionInternal(ex, error, new HttpHeaders()
				,status, request);	
	}
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		if(body == null) {
			body = Error.builder()
			.status(status.value())
			.title(status.getReasonPhrase()).build();
		}else if(body instanceof String) {
			body = Error.builder()
					.status(status.value())
					.title((String)body).build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Error.ErrorBuilder createErrorBuilder(HttpStatus status, 
			TypeErro type, String detail) {
		return Error.builder()
				.status(status.value())
				.type(type.getUri())
				.title(type.getTitle())
				.detail(detail);
	}
}
