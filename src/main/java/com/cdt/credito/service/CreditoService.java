package com.cdt.credito.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cdt.credito.dto.analiseCredito.AnaliseCreditoDTO;
import com.cdt.credito.dto.portadorCredito.FindPortadorCreditoDTO;
import com.cdt.credito.exceptions.DuplicatedRecordException;
import com.cdt.credito.exceptions.RecordNotFoundException;
import com.cdt.credito.model.Credito;
import com.cdt.credito.model.PortadorCredito;


public interface CreditoService {
	
	PortadorCredito createPortadorCredito(PortadorCredito portador) throws DuplicatedRecordException;
	
	PortadorCredito updatePortadorCredito(PortadorCredito portador) throws DuplicatedRecordException;
	
	PortadorCredito getPortadorCredito(Long id) throws RecordNotFoundException;
	
	Page<PortadorCredito> findPortadorCredito(FindPortadorCreditoDTO portador,
			Pageable pageable);
	
	void analisarCredito(AnaliseCreditoDTO params, Long idPortador) throws RecordNotFoundException;
	
	Credito getAnaliseCredito(Long idPortador) throws RecordNotFoundException;
	
}
