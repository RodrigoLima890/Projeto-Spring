package com.algaworks.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.food.domain.entities.Cidade;
import com.algaworks.food.domain.entities.Estado;
import com.algaworks.food.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.food.domain.exception.EntidadeEmUsoException;
import com.algaworks.food.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {
	
	private static final String MSG_CIDADE_EM_USO = 
			"A cidade de código %d não pode ser apagada pois esta em uso!";
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = cadastroEstado.buscar(estadoId);
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}
	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);
		}catch(EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(cidadeId);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, cidadeId));
		}
	}
	public Cidade buscar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId).orElseThrow(() ->
		new CidadeNaoEncontradaException(cidadeId));
	}
}
