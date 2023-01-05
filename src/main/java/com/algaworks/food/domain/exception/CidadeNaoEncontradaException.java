package com.algaworks.food.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;
	
	public CidadeNaoEncontradaException(String msg) {
		super(msg);
	}
	public CidadeNaoEncontradaException(Long cidadeId) {
		this(String.format("Cidade de código %d não encontrada.", cidadeId));
	}

}
