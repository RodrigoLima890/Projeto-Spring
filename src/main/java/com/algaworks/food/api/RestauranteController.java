package com.algaworks.food.api;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.food.domain.entities.Restaurante;
import com.algaworks.food.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.food.domain.repository.RestauranteRepository;
import com.algaworks.food.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {	
	@Autowired
	private RestauranteRepository restauranteRepository;
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@GetMapping
	public List<Restaurante> listar(){
		return restauranteRepository.findAll();
	}
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
		Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);
		if(restaurante != null) {
			return ResponseEntity.ok(restaurante.get());
		}
		return ResponseEntity.notFound().build();
	}
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = cadastroRestaurante.salvar(restaurante);	
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@PutMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante){
		try {
			Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
			if(restauranteAtual.isPresent()) {
				BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id", "formasPagamento", "endereco", "dataCadastro");
				Restaurante restauranteSalvo = cadastroRestaurante.salvar(restauranteAtual.get());
				return ResponseEntity.ok(restauranteSalvo);
			}
			return ResponseEntity.notFound().build();

		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().build();
			
		}
	}
	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
			@RequestBody Map<String, Object> campos){
		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
		if(restauranteAtual.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		merge(campos, restauranteAtual.get());
		return atualizar(restauranteId, restauranteAtual.get());
	}
	public void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();//pesquisar sobre objectMapper;
		Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
		
		camposOrigem.forEach((nomePropriedade,valorPropriedade)->{//pesquisar java.lang.reflect
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);//buscou e pegou a instância do campo
			field.setAccessible(true);//usado para permitir o acesso.
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);//pegou o valor do campo
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);//modificou o atributo em field para o objeto Restaurante passado como parametro;
			
		});
	}
	
}
