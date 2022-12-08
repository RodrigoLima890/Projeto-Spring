package com.algaworks.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.food.domain.entities.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long>{
	

}
