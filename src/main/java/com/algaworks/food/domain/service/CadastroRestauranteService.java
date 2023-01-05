package com.algaworks.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.food.domain.entities.Cozinha;
import com.algaworks.food.domain.entities.Restaurante;
import com.algaworks.food.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.food.domain.exception.RestauranteNaoEncontradoException;
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
		//Cozinha cozinha = cadastroCozinha.buscar(cozinhaId);
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
				.orElseThrow(()-> 
				new CozinhaNaoEncontradaException(cozinhaId));
		
		restaurante.setCozinha(cozinha);
		return restauranteRepository.save(restaurante);
	}
	public Restaurante buscar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId).orElseThrow(()->
		new RestauranteNaoEncontradoException(restauranteId));
		}
}

