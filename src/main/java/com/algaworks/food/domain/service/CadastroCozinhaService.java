package com.algaworks.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.food.domain.entities.Cozinha;
import com.algaworks.food.domain.exception.EntidadeEmUsoException;
import com.algaworks.food.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.food.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	public void excluir(Long cozinhaId) {
		try {
			cozinhaRepository.deleteById(cozinhaId);
		}catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Cozinha de código %d não foi encontrada.", cozinhaId));
		}
		catch(DataAccessResourceFailureException e) {
			throw new EntidadeEmUsoException(String.format(
					"Cozinha de código %d não pode ser removida pois esta em uso.", cozinhaId));
		}
	}

}
