package com.algaworks.food.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.food.domain.entities.Cozinha;

public interface CozinhaRepository extends JpaRepository<Cozinha, Long>{
	
	List<Cozinha> findTodasByNomeContaining(String nome);

}
