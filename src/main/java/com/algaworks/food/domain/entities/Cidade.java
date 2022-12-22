package com.algaworks.food.domain.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Entity
@Data//lombok gera equals e hashcode além dos contrutores e getters e setters
@EqualsAndHashCode(onlyExplicitlyIncluded = true)//pedido para incluir apenas o que for explicito no código
public class Cidade {
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@EqualsAndHashCode.Include
	@Id
	private Long id;
	
	@JoinColumn(nullable = false)
	private String nome;
	
	@ManyToOne
	@JoinColumn(nullable = false)//joinColumn indica que a entidade é a responsavel pelo relacionamento
	private Estado estado;

}
