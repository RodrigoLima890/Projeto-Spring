package com.algaworks.food.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)//Será incluido na representação apenas se não estiver nulo
@Getter
@Builder
public class Error {

	private Integer status;
	private String type;
	private String title;
	private String detail;
}
