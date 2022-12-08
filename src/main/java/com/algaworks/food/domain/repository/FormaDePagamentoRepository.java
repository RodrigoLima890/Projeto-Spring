package com.algaworks.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.food.domain.entities.FormaDePagamento;

public interface FormaDePagamentoRepository extends JpaRepository<FormaDePagamento, Long>{
	

}
