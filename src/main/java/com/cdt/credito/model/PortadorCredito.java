package com.cdt.credito.model;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * Modelo de Portador de Crédito, representa toda pessoa que se candidata a passar pela 
 * análise de crédito, podendo ser aprovado ou não.
 * 
 * @author arthurluckwu
 *
 */
@Data
@EqualsAndHashCode(of = "id")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "portador_credito")
public class PortadorCredito {

	@Id
	@SequenceGenerator(name = "portadorCreditoSeq", sequenceName = "portador_credito_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "portadorCreditoSeq")
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "nome", length = 80, nullable = false)
	private String nome;

	@Column(name = "email", length = 100, nullable = false)
	private String email;

	@NotNull
	@CPF
	@Column(name = "cpf", length = 11, nullable = false)
	private String cpf;

	@Column(name = "rua", length = 100)
	private String rua;

	@Column(name = "bairro", length = 60)
	private String bairro;

	@Column(name = "numero", length = 6)
	private String numero;

	@Column(name = "complemento", length = 100)
	private String complemento;

	@Column(name = "cep", length = 9)
	private String cep;

	@Column(name = "municipio", length = 100)
	private String municipio;

	@Column(name = "uf", length = 2)
	private String uf;
	
	@Column(name = "divida_serasa")
	private BigDecimal dividaSerasa;
	
	@Column(name = "divida_spc")
	private BigDecimal dividaSpc;
	
	@Column(name = "divida_cartorio")
	private BigDecimal dividaCartorio;
	
	@CreatedBy
	@Column(name = "id_usuario_cadastro", updatable = false)
	private Long idUsuarioCadastro;

	@LastModifiedBy
	@Column(name = "id_usuario_ultima_alteracao", insertable = false)
	private Long idUsuarioUltimaAlteracao;

	@NotNull
	@Column(name = "aprovado", nullable = false)
	private Boolean aprovado;
	
	@NotNull
	@Column(name = "analisado", nullable = false)
	private Boolean analisado;

	@CreatedDate
	@NotNull
	@Column(name = "data_cadastro", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataCadastro;

	@LastModifiedDate
	@Column(name = "data_ultima_alteracao", insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataUltimaAlteracao;
	
	public PortadorCredito() {
		super();
		aprovado = false;
		analisado = false;
	}

}