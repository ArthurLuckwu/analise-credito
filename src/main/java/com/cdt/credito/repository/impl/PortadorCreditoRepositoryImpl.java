package com.cdt.credito.repository.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.cdt.credito.data.repository.HibernateCriteriaUtils;
import com.cdt.credito.dto.portadorCredito.FindPortadorCreditoDTO;
import com.cdt.credito.model.PortadorCredito;
import com.cdt.credito.repository.PortadorCreditoRepositoryCustom;

import lombok.Getter;

@Repository
public class PortadorCreditoRepositoryImpl implements PortadorCreditoRepositoryCustom {

	@Getter
	@Resource
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public Page<PortadorCredito> find(FindPortadorCreditoDTO portadorDTO, Pageable pageable) {
		// Pega o Session do Hibernate para possibilitar o uso do Criteria do
		// Hibernate
		Session session = this.getEntityManager().unwrap(Session.class);
		Criteria criteria = session.createCriteria(PortadorCredito.class);

		if (portadorDTO != null) {
			if (StringUtils.isNotBlank(portadorDTO.getNome())) {
				criteria.add(Restrictions.ilike("nome", portadorDTO.getNome(), MatchMode.ANYWHERE));
			}
	
			if (StringUtils.isNotBlank(portadorDTO.getCpf())) {
				criteria.add(Restrictions.ilike("cpf", portadorDTO.getCpf(), MatchMode.START));
			}
			
			if (portadorDTO.getAprovado() != null) {
				criteria.add(Restrictions.eq("aprovado", portadorDTO.getAprovado()));
			}
			
			if (portadorDTO.getAnalisado() != null) {
				criteria.add(Restrictions.eq("analisado", portadorDTO.getAnalisado()));
			}
		}

		// Faz o "count" da query
		Long count = HibernateCriteriaUtils.getCount(criteria);
		List<PortadorCredito> list = Collections.emptyList();

		// Se o count for 0 (zero), não precisamos fazer a consulta
		if (count > 0) {
			// Limita os resultados de acordo com o "pageable"
			criteria = HibernateCriteriaUtils.applyPageableParams(criteria, pageable);
			list = criteria.list();
		}
		
		return new PageImpl<>(list, pageable, count);
	}

}
