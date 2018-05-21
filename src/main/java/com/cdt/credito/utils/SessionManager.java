package com.cdt.credito.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.cdt.credito.auth.enums.Permissao;
import com.cdt.credito.auth.model.Usuario;
import com.cdt.credito.security.AuthenticatedUser;
import com.cdt.credito.security.UserAuthentication;

@Component
public class SessionManager {

	public static HttpSession createSessionForUser(HttpServletRequest request, Usuario user, Collection<Permissao> userPermissions) {
		if (user == null) {
			throw new IllegalArgumentException("Usuário não  pode ser nulo");
		}

		HttpSession session = request.getSession();
		// Check if the session already exists and if true logout and create new
		if (!session.isNew()) {
			invalidateUserSession(request);
			session = request.getSession(true);
		}

		AuthenticatedUser authenticatedUser = new AuthenticatedUser();
		authenticatedUser.setId(user.getId());
		authenticatedUser.setProfileId(user.getPerfil().getId());
		BeanUtils.copyProperties(user, authenticatedUser, "senha");

		Set<String> permissionNames;
		if (userPermissions != null) {
			permissionNames = new HashSet<String>();
			for (Permissao permission : userPermissions) {
				addPermission(permission, permissionNames);
			}
			authenticatedUser.setAuthorities(permissionNames.toArray(new String[0]));
		}

		Authentication authentication = new UserAuthentication(authenticatedUser);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return session;
	}
	
	private static void addPermission(Permissao permission, Set<String> permissionNames) {
		if (permission.getParentPermission() != null) {
			addPermission(permission.getParentPermission(), permissionNames);
		}
		permissionNames.add(permission.name());
	}

	public static void invalidateUserSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(null);

		SecurityContextHolder.clearContext();
	}

}