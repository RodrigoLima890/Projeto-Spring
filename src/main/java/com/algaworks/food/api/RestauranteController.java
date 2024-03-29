package com.algaworks.food.api;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.food.domain.entities.Restaurante;
import com.algaworks.food.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.food.domain.exception.NegocioException;
import com.algaworks.food.domain.repository.RestauranteRepository;
import com.algaworks.food.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
	public Restaurante buscar(@PathVariable Long restauranteId) {
			return cadastroRestaurante.buscar(restauranteId);		
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody Restaurante restaurante) {
		try {
			return cadastroRestaurante.salvar(restaurante);			
		}catch(CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("/{restauranteId}")
	public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante, HttpServletRequest request){
		try {
			Restaurante restauranteAtual = cadastroRestaurante.buscar(restauranteId);	
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco","dataCadastro", "produtos");

			return cadastroRestaurante.salvar(restauranteAtual);
		}catch(CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PatchMapping("/{restauranteId}")
	public Restaurante atualizarParcial(@PathVariable Long restauranteId,
			@RequestBody Map<String, Object> campos, HttpServletRequest request){
		Restaurante restauranteAtual = cadastroRestaurante.buscar(restauranteId);
		
		merge(campos, restauranteAtual, request);
		return atualizar(restauranteId, restauranteAtual, request);
	}
	
	public void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino,
			HttpServletRequest request) {
		ServletServerHttpRequest serverRequest = new ServletServerHttpRequest(request);
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();//pesquisar sobre objectMapper;
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			
			Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
			
			camposOrigem.forEach((nomePropriedade,valorPropriedade)->{//pesquisar java.lang.reflect
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);//buscou e pegou a instância do campo
				field.setAccessible(true);//usado para permitir o acesso.
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);//pegou o valor do campo
				
				ReflectionUtils.setField(field, restauranteDestino, novoValor);//modificou o atributo em field para o objeto Restaurante passado como parametro;	
		});
		}catch(IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverRequest);
		}
	}
	
}
