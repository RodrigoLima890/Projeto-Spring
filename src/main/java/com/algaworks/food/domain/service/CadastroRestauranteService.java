package com.algaworks.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.food.domain.entities.Cozinha;
import com.algaworks.food.domain.entities.Restaurante;
import com.algaworks.food.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.food.domain.repository.CozinhaRepository;
import com.algaworks.food.domain.repository.RestauranteRepository;
@Service
public class CadastroRestauranteService {
	@Autowired
	private RestauranteRepository restauranteRepository;
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
				.orElseThrow(()-> 
				new EntidadeNaoEncontradaException(String.format("Cozinha de código %d não encontrada.", cozinhaId)));
		
		restaurante.setCozinha(cozinha);
		return restauranteRepository.save(restaurante);
	}
}
