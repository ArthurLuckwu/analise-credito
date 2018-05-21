package com.cdt.credito.auth.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Modelo de Usuários do sistema.
 * 
 * @author arthurluckwu
 *
 */
@Data
@EqualsAndHashCode(of = "id")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "usuario")
public class Usuario {

	@Id
	@SequenceGenerator(name = "usuarioSeq", sequenceName = "usuario_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarioSeq")
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "nome", length = 80, nullable = false)
	private String nome;

	@NotNull
	@Column(name = "email", length = 100, nullable = false)
	private String email;

	@NotNull
	@Column(name = "ativo", nullable = false)
	private Boolean ativo;

	@NotNull
	@CPF
	@Column(name = "cpf", length = 11, nullable = false)
	private String cpf;

	@Column(name = "senha")
	private String senha;

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
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="id_perfil", nullable = false)
	private Perfil perfil;

	@Column(name = "ultimo_login", insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar ultimoLogin;

	@Column(name = "ip_ultimo_login", length = 100, nullable = false)
	private String ipUltimoLogin;

	@NotNull
	@Column(name = "excluido", nullable = false)
	private Boolean excluido;

	@CreatedDate
	@NotNull
	@Column(name = "data_cadastro", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataCadastro;

	@LastModifiedDate
	@Column(name = "data_ultima_alteracao", insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataUltimaAlteracao;
	
	public Usuario() {
		super();
		excluido = false;
		ativo = true;
	}

}