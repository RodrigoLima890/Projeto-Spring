package com.algaworks.food.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.food.domain.entities.Cidade;
import com.algaworks.food.domain.entities.Estado;
import com.algaworks.food.domain.exception.EntidadeEmUsoException;
import com.algaworks.food.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.food.domain.repository.CidadeRepository;
import com.algaworks.food.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Optional<Estado> estado = estadoRepository.findById(estadoId);
		if (estado == null) {
            throw new EntidadeNaoEncontradaException(
                String.format("Não existe cadastro de estado com código %d", estadoId));
        }
		cidade.setEstado(estado.get());
		return cidadeRepository.save(cidade);
	}
	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);
		}catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("A Cidade de código %d não foi encontrada! ", cidadeId));
		}catch(DataAccessResourceFailureException e) {
			throw new EntidadeEmUsoException(String.format("A cidade de código %d não pode ser apagada pois esta em uso!", cidadeId));
		}
	}
	

}
