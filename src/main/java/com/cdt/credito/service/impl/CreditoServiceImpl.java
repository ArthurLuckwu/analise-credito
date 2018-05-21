package com.cdt.credito.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.audit4j.core.annotation.Audit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cdt.credito.dto.analiseCredito.AnaliseCreditoDTO;
import com.cdt.credito.dto.portadorCredito.FindPortadorCreditoDTO;
import com.cdt.credito.exceptions.DuplicatedRecordException;
import com.cdt.credito.exceptions.RecordNotFoundException;
import com.cdt.credito.model.Credito;
import com.cdt.credito.model.PortadorCredito;
import com.cdt.credito.repository.CreditoRepository;
import com.cdt.credito.repository.PortadorCreditoRepository;
import com.cdt.credito.service.CreditoService;


@Service
public class CreditoServiceImpl implements CreditoService {

	private static final String MSG_NOT_FOUND_PORTADOR = "Portador não encontrado.";
	private static final String MSG_DUPLICATED_PORTADOR = "Já existe um portador de crédito com esses dados.";
	
	@Resource
	private PortadorCreditoRepository portadorCreditoRepository;
	
	@Resource
	private CreditoRepository creditoRepository;

	@Override
	@Audit
	@Transactional
	public PortadorCredito createPortadorCredito(PortadorCredito portador) throws DuplicatedRecordException {
		return this.savePortadorCredito(portador);
	}

	@Override
	@Audit
	@Transactional
	public PortadorCredito updatePortadorCredito(PortadorCredito portador) throws DuplicatedRecordException {
		return this.savePortadorCredito(portador);
	}

	private PortadorCredito savePortadorCredito(PortadorCredito portador) throws DuplicatedRecordException {
		if (isPortadorNameRepeated(portador.getId(), portador.getNome())) {
			throw new DuplicatedRecordException(String.format(MSG_DUPLICATED_PORTADOR, portador.getNome()), "nome");
		}

		return this.portadorCreditoRepository.save(portador);
	}

	// Check if the Profile name is repeated
	private boolean isPortadorNameRepeated(Long id, String nome) {
		PortadorCredito portador = this.portadorCreditoRepository.findFirstByNomeIgnoreCase(nome);

		return (portador != null) && (!portador.getId().equals(id));
	}

	
	@Override
	public PortadorCredito getPortadorCredito(Long id) throws RecordNotFoundException {
		PortadorCredito portador = this.portadorCreditoRepository.findOne(id);
		if (portador == null) {
			throw new RecordNotFoundException(MSG_NOT_FOUND_PORTADOR);
		}
		return portador;
	}

	@Override
	public Page<PortadorCredito> findPortadorCredito(FindPortadorCreditoDTO portador, Pageable pageable) {
		return this.portadorCreditoRepository.find(portador, pageable);
	}

	@Override
	public void analisarCredito(AnaliseCreditoDTO params, Long idPortador) throws RecordNotFoundException {
		
		PortadorCredito portador = this.getPortadorCredito(idPortador);
		portador.setAnalisado(true);
		if (params.isAprovado()) {
			portador.setAprovado(true);
			
			Credito credito = new Credito();
			credito.setPortadorCredito(portador);
			credito.setCreditoConcedido(params.getCreditoConcedido());
			this.creditoRepository.save(credito);
		}
		
		this.portadorCreditoRepository.save(portador);
	}

	@Override
	public Credito getAnaliseCredito(Long idPortador) throws RecordNotFoundException {
		PortadorCredito portador = this.portadorCreditoRepository.getOne(idPortador);
		Credito credito = this.creditoRepository.findByPortadorCredito(portador);
		if (credito == null) {
			Credito c = new Credito();
			c.setPortadorCredito(portador);
//			throw new RecordNotFoundException(MSG_NOT_FOUND_PORTADOR);
			return c;
		} 
		return credito;
	}

}
