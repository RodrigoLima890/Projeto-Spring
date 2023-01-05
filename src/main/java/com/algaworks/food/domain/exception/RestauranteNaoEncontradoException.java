package com.algaworks.food.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;
	
	public RestauranteNaoEncontradoException(String msg) {
		super(msg);
	}
	public RestauranteNaoEncontradoException(Long restauranteId) {
		this(String.format("Restaurante de código %d não encontrado.", restauranteId));
	}

}
