package com.algaworks.food.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.food.domain.exception.EntidadeEmUsoException;
import com.algaworks.food.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.food.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

@ControllerAdvice
public class ApiExeptionHandler extends ResponseEntityExceptionHandler{
	
	private static final String MSG_GENERICA_ERRO_USUARIO = "Erro interno, tente novamente se persistir o problema entre em contato "
			+ "com o administrador do sistema";

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleExceptionInternal(Exception ex, WebRequest request) throws Exception {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		
		TypeErro typeErro = TypeErro.ERRO_DE_SISTEMA;
		String detail = MSG_GENERICA_ERRO_USUARIO;
		
		ex.printStackTrace();
		
		Error erro = createErrorBuilder(status, typeErro, detail).userMessage(MSG_GENERICA_ERRO_USUARIO).build();
		return handleExceptionInternal(ex, erro, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(
			NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		TypeErro typeErro = TypeErro.RECURSO_NAO_ENCONTRADO;
	
		String resourceName = ex.getRequestURL();
		String detail = String.format("Recurso %s não existe", resourceName);
		Error error = createErrorBuilder(status, typeErro, detail).userMessage(MSG_GENERICA_ERRO_USUARIO).build();
		return handleExceptionInternal(ex, error, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(
			TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		if(ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException)ex, headers, status, request);
		}
		
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if(rootCause instanceof InvalidFormatException ) {
			return handleInvalidFormatException((InvalidFormatException)rootCause, headers, status, request);
		}else if(rootCause instanceof IgnoredPropertyException) {
			return handleIgnoredPropertyException((IgnoredPropertyException)rootCause, headers, status, request);
		}else if(rootCause instanceof UnrecognizedPropertyException) {
			return handleUnrecognizedPropertyException((UnrecognizedPropertyException)rootCause, headers, status, request);
		}
		TypeErro typeError = TypeErro.MENSAGEM_COM_ERROR;
		String details = "O Corpo Da Requisição Está Com Error, Verifique A Mensagem";
		
		Error error = createErrorBuilder(status, typeError, details).userMessage(MSG_GENERICA_ERRO_USUARIO).build();
		
		return handleExceptionInternal(ex, error, new HttpHeaders()
				,status, request);	
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
			HttpHeaders header, HttpStatus status, WebRequest request) {
		TypeErro typeError = TypeErro.PARAMETRO_INVALIDO;
		String name = ex.getName();
		String param = ex.getParameter().getParameterName();
		String paramType = ex.getRequiredType().getSimpleName();
		
		String datail = String.format("O parâmetro da URL %s recebeu %s que é um tipo inválido."
				+ "Corrija e informe um valor do tipo %s", name,param, paramType);
		
		Error error = createErrorBuilder(status, typeError, datail).userMessage(MSG_GENERICA_ERRO_USUARIO).build();
		return handleExceptionInternal(ex, error, header, status, request);
	}

	private ResponseEntity<Object> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		TypeErro typeError = TypeErro.MENSAGEM_COM_ERROR;
		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		Object classe = ex.getReferringClass().getSimpleName();
		String details = String.format("A propriedade %s não existe em %s", path, classe);
		Error error = createErrorBuilder(status, typeError,details).userMessage(MSG_GENERICA_ERRO_USUARIO).build();
		return handleExceptionInternal(ex, error, headers, status, request);
	}

	private ResponseEntity<Object> handleIgnoredPropertyException(IgnoredPropertyException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		TypeErro typeError = TypeErro.MENSAGEM_COM_ERROR;
		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		String details = String.format("Campo %s não habilitado", path);
		Error error = createErrorBuilder(status, typeError, details).userMessage(MSG_GENERICA_ERRO_USUARIO)
				.build();
		return handleExceptionInternal(ex, error, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		TypeErro typeError = TypeErro.MENSAGEM_COM_ERROR;
		
		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		
		String details = String.format("A propriedade '%s' recebeu um valor '%s'"
				+ "deve receber um tipo '%s'", path,ex.getValue(),ex.getTargetType().getSimpleName());
		Error error = createErrorBuilder(status, typeError, details)
				.userMessage(MSG_GENERICA_ERRO_USUARIO).build();
		
		return handleExceptionInternal(ex, error, new HttpHeaders()
				,status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		String details = ex.getMessage();
		TypeErro typeError = TypeErro.RECURSO_NAO_ENCONTRADO;
		
		Error error = createErrorBuilder(status, typeError, details).userMessage(details).build();
		
		return handleExceptionInternal(ex, error, new HttpHeaders()
				,status, request);	
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request){
		HttpStatus status = HttpStatus.CONFLICT;
		String details = ex.getMessage();
		TypeErro typeError = TypeErro.ENTIDADE_EM_USO;
		Error error = createErrorBuilder(status, typeError, details).userMessage(MSG_GENERICA_ERRO_USUARIO).build();
		
		return handleExceptionInternal(ex, error, new HttpHeaders()
				,status, request);	
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		String details = ex.getMessage();
		TypeErro typeErro = TypeErro.ERROR_DE_NEGOCIO;
		Error error = createErrorBuilder(status, typeErro, details).userMessage(MSG_GENERICA_ERRO_USUARIO).build();
		return handleExceptionInternal(ex, error, new HttpHeaders()
				,status, request);	
	}
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		if(body == null) {
			body = Error.builder()
					.timestamp(LocalDateTime.now())
					.status(status.value())
					.title(status.getReasonPhrase()).build();
		}else if(body instanceof String) {
			body = Error.builder()
					.timestamp(LocalDateTime.now())
					.status(status.value())
					.title((String)body)
					.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Error.ErrorBuilder createErrorBuilder(HttpStatus status, 
			TypeErro type, String detail) {
		return Error.builder()
				.status(status.value())
				.type(type.getUri())
				.title(type.getTitle())
				.detail(detail)
				.timestamp(LocalDateTime.now());
	}
}
