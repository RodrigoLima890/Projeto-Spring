package com.algaworks.food.api;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.food.domain.entities.Cozinha;
import com.algaworks.food.domain.repository.CozinhaRepository;
import com.algaworks.food.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@GetMapping
	public List<Cozinha> listar(){
		return cozinhaRepository.findAll();
	}
	
	@GetMapping("/{cozinhaId}")
	public Cozinha buscar(@PathVariable(value = "cozinhaId") Long cozinhaId) {
		return cadastroCozinha.buscar(cozinhaId);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return cadastroCozinha.salvar(cozinha);
	}
	
	@PutMapping("/{cozinhaId}")
	public Cozinha atualizar(@PathVariable Long cozinhaId,@RequestBody Cozinha cozinha){
		Cozinha cozinhaAtual = cadastroCozinha.buscar(cozinhaId);
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		return cadastroCozinha.salvar(cozinhaAtual);	
	}
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long cozinhaId){
		cadastroCozinha.excluir(cozinhaId);
	}

}
