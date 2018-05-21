package com.cdt.credito.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdt.credito.model.PortadorCredito;

public interface PortadorCreditoRepository extends JpaRepository<PortadorCredito, Long>, PortadorCreditoRepositoryCustom {

	PortadorCredito findFirstByNomeIgnoreCase(String nome);

}
