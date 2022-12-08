package com.algaworks.food.infrastructure.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.algaworks.food.domain.repository.CustomJpaRepository;

public class CustomJpaRepositoryImpl<T,ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID>{

	private EntityManager manager;
	
	public CustomJpaRepositoryImpl(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		// TODO Auto-generated constructor stub
	}

	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.manager = entityManager;
	}




	@Override
	public Optional<T> buscarPrimeiro() {
		String jpql = "from "+getDomainClass().getName();
		
		T entity = manager.createQuery(jpql, getDomainClass())
				.setMaxResults(1)
				.getSingleResult();
		
		return Optional.ofNullable(entity);
	}
	

}
