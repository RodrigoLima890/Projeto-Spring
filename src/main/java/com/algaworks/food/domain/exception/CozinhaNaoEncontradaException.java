package com.algaworks.food.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;
	
	public CozinhaNaoEncontradaException(String msg) {
		super(msg);
	}
	public CozinhaNaoEncontradaException(Long cozinhaId) {
		this(String.format("Cozinha de código %d não encontrada.", cozinhaId));
	}

}
