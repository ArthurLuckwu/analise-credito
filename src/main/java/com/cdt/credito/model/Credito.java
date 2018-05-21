package com.cdt.credito.model;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**	
 * Modelo de Crédito, utilizado quando há aprovação de liberação de crédito após análise de crédito.
 * 
 * @author arthurluckwu
 *
 */
@Data
@EqualsAndHashCode(of = "id")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "credito")
public class Credito {

	@Id
	@SequenceGenerator(name = "creditoSeq", sequenceName = "credito_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "creditoSeq")
	@Column(name = "id")
	private Long id;

	@NotNull
	@OneToOne
	@JoinColumn(name="id_portador_credito", nullable = false)
	private PortadorCredito portadorCredito;

	@NotNull
	@Column(name = "credito_concedido")
	private BigDecimal creditoConcedido;

	@CreatedBy
	@Column(name = "id_usuario_cadastro", updatable = false)
	private Long idUsuarioCadastro;

	@CreatedDate
	@NotNull
	@Column(name = "data_cadastro", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataCadastro;

	public Credito() {
		super();
	}

}