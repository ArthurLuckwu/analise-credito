package com.cdt.credito.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cdt.credito.dto.portadorCredito.FindPortadorCreditoDTO;
import com.cdt.credito.model.PortadorCredito;


public interface PortadorCreditoRepositoryCustom {

	Page<PortadorCredito> find(FindPortadorCreditoDTO portadorDTO, Pageable pageable);

}
