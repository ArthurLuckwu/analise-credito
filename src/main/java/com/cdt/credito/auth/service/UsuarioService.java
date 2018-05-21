package com.cdt.credito.auth.service;

import javax.security.auth.login.CredentialExpiredException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;

import com.cdt.credito.auth.model.Perfil;
import com.cdt.credito.auth.model.Usuario;
import com.cdt.credito.exceptions.RecordNotFoundException;


public interface UsuarioService {

	Usuario findUsuarioByEmail(String email);

	Usuario getUsuario(Long id) throws RecordNotFoundException;
	
	Perfil getPerfil(Long id) throws RecordNotFoundException;

	Usuario login(String email, String senha) throws BadCredentialsException, CredentialExpiredException, AccessDeniedException;
}
