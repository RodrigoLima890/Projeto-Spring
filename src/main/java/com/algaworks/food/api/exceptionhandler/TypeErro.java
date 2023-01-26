package com.algaworks.food.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum TypeErro {
	RECURSO_NAO_ENCONTRADO("Recurso Não Encontrado", "/recurso-nao-encontrado"),
	ENTIDADE_EM_USO("Entidade Em Uso", "/entidade-em-uso"),
	ERROR_DE_NEGOCIO("Violação Das Regras De Negócio","/error-negocio"),
	MENSAGEM_COM_ERROR("Erro Na Mensagem","/mensagem-com-error"),
	PARAMETRO_INVALIDO("Parâmetro inválido","/parametro-invalido"),
	ERRO_DE_SISTEMA("Erro No Sistema","/erro-no-sistema");
	
	private String title;
	private String uri;
	
	private TypeErro(String title, String path) {
		this.title = title;
		this.uri = "https://food.com.br"+path;
	}
	
	

}
