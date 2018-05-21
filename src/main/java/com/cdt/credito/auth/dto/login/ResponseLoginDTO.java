package com.cdt.credito.auth.dto.login;

import java.util.HashSet;
import java.util.Set;

import com.cdt.credito.auth.enums.Permissao;
import com.cdt.credito.auth.model.Perfil;
import com.cdt.credito.auth.model.Usuario;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "LoginResponse", description = "Encapsulates user data to be used by the application")
public class ResponseLoginDTO {

	@ApiModelProperty(value = "The user ID")
	private Long id;

	@ApiModelProperty(value = "The user name")
	private String nome;

	@ApiModelProperty(value = "The user e-mail address")
	private String email;
	
	@ApiModelProperty(value = "The user profile")
	private ResponseLoginPerfilDTO perfil;

	@ApiModelProperty(value = "The list of the permissions that the user has")
	private Set<Permissao> permissoes;

	public ResponseLoginDTO(Usuario user) {
		super();
		this.id = user.getId();
		this.nome = user.getNome();
		this.email = user.getEmail();
		this.perfil = new ResponseLoginPerfilDTO(user.getPerfil());
		this.permissoes = new HashSet<>();
		for (Permissao permission : user.getPerfil().getPermissoes()) {
			addPermission(permission);
		}
	}
	
	private void addPermission(Permissao permission) {
		if (permission.getParentPermission() != null) {
			addPermission(permission.getParentPermission());
		}
		this.permissoes.add(permission);
	}
	
	@Getter
	@Setter
	@ApiModel(value = "LoginPerfilResponse", description = "Encapsulates user profile data to be used by the application")
	public class ResponseLoginPerfilDTO {

		@ApiModelProperty(value = "The user profile ID")
		private Long id;

		@ApiModelProperty(value = "The user profile name")
		private String nome;

		public ResponseLoginPerfilDTO(Perfil perfil) {
			super();
			this.id = perfil.getId();
			this.nome = perfil.getNome();
		}
	}
}
