package com.algaworks.food.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.food.domain.entities.Cozinha;
import com.algaworks.food.domain.entities.Restaurante;
import com.algaworks.food.domain.repository.CozinhaRepository;
import com.algaworks.food.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {

	@Autowired
	public CozinhaRepository cozinhaRepository;
	@Autowired
	public RestauranteRepository restauranteRepository;
	
	@GetMapping("/cozinhas/por-nome")
	public List<Cozinha> findByNome(String nome){
		return cozinhaRepository.findTodasByNomeContaining(nome);
	
	}
	@GetMapping("/restaurantes/por-taxa-frete")
	public List<Restaurante> findByTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal){
		return restauranteRepository.findBytaxaFreteBetween(taxaInicial, taxaFinal);
	}
	@GetMapping("/restaurantes/por-nome-e-cozinha")
	public List<Restaurante> findByTaxaFreteAndCozinhaId(String nome, Long cozinhaId){
		return restauranteRepository.cosultarPorNome(nome, cozinhaId);
	}
	@GetMapping("/restaurantes/primeiro-por-nome")
	public Optional<Restaurante> findFirstByNome(String nome){
		return restauranteRepository.findFirstByNomeContaining(nome);
	}
	@GetMapping("/restaurantes/por-nome-top2")
	public List<Restaurante> findTop2ByName(String nome){
		return restauranteRepository.findTop2ByNomeContaining(nome);
	}
	@GetMapping("/restaurantes/contar-restaurante-por-cozinhas")
	public int countByCozinha(Long cozinhaId){
		return restauranteRepository.countByCozinhaId(cozinhaId);
	}
	@GetMapping("/restaurantes/por-nome-taxa-frete")
	public List<Restaurante> findByNomeAndTaxaFrete(String nome,BigDecimal taxaInicial, BigDecimal taxaFinal){
		return restauranteRepository.find(nome,taxaInicial, taxaFinal);
	}
	@GetMapping("/restaurantes/com-frete-gratis")
	public List<Restaurante> restaurantesComFreteGratis(String nome){
		return restauranteRepository.findComFreteGratis(nome);
	}
	@GetMapping("restaurantes/buscar-primeiro")
	public Optional<Restaurante> buscarPrimeiro(){
		return restauranteRepository.buscarPrimeiro();
	}
}
