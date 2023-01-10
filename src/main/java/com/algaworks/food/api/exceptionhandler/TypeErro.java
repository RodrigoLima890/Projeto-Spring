package com.algaworks.food.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum TypeErro {
	ENTIDADE_NAO_ENCONTRADA("Entidade Não Encontrada", "/entidade-nao-encontrada"),
	ENTIDADE_EM_USO("Entidade Em Uso", "/entidade-em-uso"),
	ERROR_DE_NEGOCIO("Violação Das Regras De Negócio","/error-negocio");
	
	private String title;
	private String uri;
	
	private TypeErro(String title, String path) {
		this.title = title;
		this.uri = "https://food.com.br"+path;
	}
	
	

}
