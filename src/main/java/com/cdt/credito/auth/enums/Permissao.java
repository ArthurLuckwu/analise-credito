package com.cdt.credito.auth.enums;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import lombok.AccessLevel;
import lombok.Getter;
/**
 * Permissões para controle de acesso ao sistema.
 * 
 * @author arthurluckwu
 *
 */
@Getter
public enum Permissao {
	/* Private Authorization System */
	GUEST(null, false, true), 
	LOGGED_USER(GUEST, false, true),

	/* Module: Master Data */
	MODULO_PROPOSTAS(LOGGED_USER, false),
	CADASTRAR(MODULO_PROPOSTAS),
	CONSULTAR(MODULO_PROPOSTAS),
	VERIFICAR(MODULO_PROPOSTAS),
	;

	private final Permissao parentPermission;
	@Getter(AccessLevel.NONE)
	private final Set<Permissao> childPermissions;
	private final boolean selectable;
	private final boolean hidden;

	Permissao(Permissao parentPermission, boolean selectable, boolean hidden) {
		this.childPermissions = new HashSet<>();
		this.selectable = selectable;
		this.hidden = hidden;

		this.parentPermission = parentPermission;
		if (parentPermission != null) {
			parentPermission.childPermissions.add(this);
		}
	}

	Permissao(Permissao parentPermission, boolean selectable) {
		this(parentPermission, selectable, false);
	}

	Permissao(Permissao parentPermission) {
		this(parentPermission, true);
	}

	public Set<Permissao> getChildPermissions() {
		return Collections.unmodifiableSet(this.childPermissions);
	}

}
