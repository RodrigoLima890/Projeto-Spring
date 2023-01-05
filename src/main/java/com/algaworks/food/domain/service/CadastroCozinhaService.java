package com.algaworks.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.food.domain.entities.Cozinha;
import com.algaworks.food.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.food.domain.exception.EntidadeEmUsoException;
import com.algaworks.food.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {
	
	private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida pois esta em uso.";
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	public void excluir(Long cozinhaId) {
		try {
			cozinhaRepository.deleteById(cozinhaId);
		}catch(EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(cozinhaId);
		}
		catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(
					MSG_COZINHA_EM_USO, cozinhaId));
		}
	}
	
	public Cozinha buscar(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId).orElseThrow(()->
		new CozinhaNaoEncontradaException(cozinhaId));
	}

}
