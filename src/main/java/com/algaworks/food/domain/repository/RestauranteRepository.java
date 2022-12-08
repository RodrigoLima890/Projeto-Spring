package com.algaworks.food.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import com.algaworks.food.domain.entities.Restaurante;

public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>,
RestauranteQuery,
JpaSpecificationExecutor<Restaurante>{

	List<Restaurante> findBytaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	//@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> cosultarPorNome(String nome, @Param("id")Long cozinhaId);
	
	Optional<Restaurante> findFirstByNomeContaining(String nome);
	
	List<Restaurante> findTop2ByNomeContaining(String nome);
	
	int countByCozinhaId(Long cozinhaId);
	
	List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
}
