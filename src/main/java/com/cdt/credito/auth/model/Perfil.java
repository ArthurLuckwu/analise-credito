package com.cdt.credito.auth.model;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.cdt.credito.auth.enums.Permissao;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Modelo de Perfil, utilizado para controle de acesso dos usuários ao sistema web e APIs. 
 *
 * @author arthurluckwu
 *
 */
@Data
@EqualsAndHashCode(of = "id")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "perfil")
public class Perfil {

	@Id
	@SequenceGenerator(name = "perfilSeq", sequenceName = "perfil_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfilSeq")
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "nome", length = 30, nullable = false, unique = true)
	private String nome;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
    @ElementCollection(targetClass=Permissao.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="perfil_permissao", joinColumns = {@JoinColumn(name="id_perfil")})
    @Column(name="permissao")
	private Set<Permissao> permissoes;

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

	public Perfil() {
		super();
		excluido = false;
	}

	public Set<Permissao> getPermissoes() {
		if (this.permissoes == null) {
			this.permissoes = new HashSet<>();
		}
		return Collections.unmodifiableSet(this.permissoes);
	}

	public boolean addPermissoes(Permissao permissao) {
		if (this.permissoes == null) {
			this.permissoes = new HashSet<>();
		}

		return this.permissoes.add(permissao);
	}

	public boolean removePermissao(Permissao permissao) {
		if (this.permissoes != null) {
			return this.permissoes.remove(permissao);
		}
		return false;
	}

	public boolean updatePermissoes(Collection<Permissao> permissoes) {
		if (this.permissoes == null) {
			this.permissoes = new HashSet<>();
		}

		/*
		 * Lógica necessária para evitar a exclusão de todos os elementos e
		 * posterior adição dos mesmos, isso é apenas uma otimização para
		 * reduzir a quantidade de registros modificados no banco de dados.
		 */
		// Adiciona os itens informados aos existentes
		boolean result = this.permissoes.addAll(permissoes);
		// Remove dos itens atuais os que não estão presentes nos itens informados
		result = this.permissoes.retainAll(permissoes) || result;

		return result;
	}

}
