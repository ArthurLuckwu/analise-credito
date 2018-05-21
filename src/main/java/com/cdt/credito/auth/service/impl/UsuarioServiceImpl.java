package com.cdt.credito.auth.service.impl;

import java.util.Calendar;

import javax.annotation.Resource;
import javax.security.auth.login.CredentialExpiredException;
import javax.transaction.Transactional;

import org.audit4j.core.annotation.Audit;
import org.audit4j.core.annotation.IgnoreAudit;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.cdt.credito.auth.model.Perfil;
import com.cdt.credito.auth.model.Usuario;
import com.cdt.credito.auth.repository.PerfilRepository;
import com.cdt.credito.auth.repository.UsuarioRepository;
import com.cdt.credito.auth.service.UsuarioService;
import com.cdt.credito.exceptions.RecordNotFoundException;
import com.cdt.credito.utils.AuthUtils;

import lombok.Getter;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private static final String MSG_BAD_CREDENTIALS = "E-mail ou senha inválidos.";
	private static final String MSG_ACCOUNT_DEACTIVATED = "A conta de usuário está desativada.";
	private static final String MGS_ACCESS_DENIED = "Acesso negado. Verifique se o seu usuário possui um perfil válido.";
	private static final String MSG_DUPLICATED_PROFILE = "Já existe um Perfil com esse nome.";
	private static final String MSG_NOT_FOUND_PROFILE = "Perfil não encontrado.";
	private static final String MSG_DUPLICATED_USER_EMAIL = "Já existe um Usuário com este e-mail.";
	private static final String MSG_NOT_FOUND_USER = "Usuário não encontrado.";
	private static final String MSG_NOT_FOUND_USER_TOKEN = "Token não encontrado ou inválido.";
	private static final String MSG_WEAK_PASSWORD = "Senha fraca. A senha deve conter letras e números.";
	
	@Resource
	private PerfilRepository perfilRepository;

	@Getter
	@Resource
	private UsuarioRepository usuarioRepository;

	@Override
	public Perfil getPerfil(Long id) throws RecordNotFoundException {
		Perfil perfil = this.perfilRepository.getOne(id);
		if (perfil == null) {
			throw new RecordNotFoundException(MSG_NOT_FOUND_PROFILE);
		}
		return perfil;
	}

	@Override
	public Usuario getUsuario(Long id) throws RecordNotFoundException {
		Usuario user = this.usuarioRepository.findOne(id);
		if (user == null) {
			throw new RecordNotFoundException(MSG_NOT_FOUND_USER);
		}
		return user;
	}

	@Override
	public Usuario findUsuarioByEmail(String email) {
		return this.usuarioRepository.findFirstByEmailIgnoreCaseAndExcluidoFalse(email);
	}

	@Override
	@Audit
	@Transactional
	public Usuario login(String email, @IgnoreAudit String password)
			throws BadCredentialsException, CredentialExpiredException, AccessDeniedException {
		Usuario user = this.findUsuarioByEmail(email);
		
		// Check user password
		if ((user == null) || !AuthUtils.isPasswordValid(user.getSenha(), password) || email == null || password == null) {
			throw new BadCredentialsException(MSG_BAD_CREDENTIALS);
		}

		if (!user.getAtivo()) {
			throw new CredentialExpiredException(MSG_ACCOUNT_DEACTIVATED);
		}

		Perfil userProfile = user.getPerfil();
		if (userProfile.getExcluido()) {
			userProfile = null;
		}

		// Check if the user profile is valid
		if (userProfile == null) {
			throw new AccessDeniedException(MGS_ACCESS_DENIED);
		}

		// if (usuario.isPasswordExpired()) {
		// if (usuario.getAtivo()) {
		// throw new CredentialExpiredException(
		// String.format("LOGIN FAILED - Senha expirada: Usuário: '%s'.",
		// usuario.getEmail()));
		// } else {
		// throw new CredentialExpiredException(String.format(
		// "LOGIN FAILED - A conta do usuário precisa ser ativada: Usuário:
		// '%s'.",
		// usuario.getEmail()));
		// }
		// }

		// Remove all "Password Definition Token" if they exist. if the user has logged in the password do not need to be changed.

		user.setUltimoLogin(Calendar.getInstance());
		return this.getUsuarioRepository().save(user);
	}

}
