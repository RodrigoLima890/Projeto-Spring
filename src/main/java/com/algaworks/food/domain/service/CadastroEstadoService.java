package com.algaworks.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.food.domain.entities.Estado;
import com.algaworks.food.domain.exception.EntidadeEmUsoException;
import com.algaworks.food.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.food.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	public void excluir(Long estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
		}catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Estado de código %d não encontrado.", estadoId));
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Estado de código %d não pode ser excluido pois já esta em uso", estadoId));
		}
	}
	

}
