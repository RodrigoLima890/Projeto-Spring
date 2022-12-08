package com.algaworks.food.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algaworks.food.domain.entities.Restaurante;

public interface RestauranteQuery {

	List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	
	List<Restaurante> findComFreteGratis(String nome);
}
