package com.cdt.credito.auth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cdt.credito.auth.model.Perfil;
import com.cdt.credito.data.repository.SoftDeleteJpaRepository;

public interface PerfilRepository extends SoftDeleteJpaRepository<Perfil, Long> {

	Perfil findFirstByNomeIgnoreCaseAndExcluidoFalse(String nome);
	
	Page<Perfil> findByNomeContainingIgnoreCaseAndExcluidoFalse(String nome, Pageable pageable);

}
