package com.cdt.credito.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdt.credito.model.Credito;
import com.cdt.credito.model.PortadorCredito;

public interface CreditoRepository extends JpaRepository<Credito, Long> {

	Credito findByPortadorCredito(PortadorCredito portador);
}
