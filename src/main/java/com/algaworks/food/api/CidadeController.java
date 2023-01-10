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

import com.algaworks.food.domain.entities.Cidade;
import com.algaworks.food.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.food.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.food.domain.exception.NegocioException;
import com.algaworks.food.domain.repository.CidadeRepository;
import com.algaworks.food.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@GetMapping
	public List<Cidade> listar(){
		return cidadeRepository.findAll();
	}
	
	@GetMapping("/{cidadeId}")
	public Cidade buscar(@PathVariable Long cidadeId){
		return cadastroCidade.buscar(cidadeId);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cidade salvar(@RequestBody Cidade cidade){
		try {
			return cadastroCidade.salvar(cidade);
		}catch(EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	@PutMapping("/{cidadeId}")
	public Cidade atualizar(@RequestBody Cidade cidade,@PathVariable Long cidadeId){
		Cidade cidadeAtual = cadastroCidade.buscar(cidadeId);
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		try {
			return cadastroCidade.salvar(cidadeAtual);	
		}catch(EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		
	}
	
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long cidadeId){	
		cadastroCidade.excluir(cidadeId);
	}
	
	
}
