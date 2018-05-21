package com.cdt.credito.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cdt.credito.security.AuthenticatedUser;

public class SecurityUtils {

	public static AuthenticatedUser getAuthenticatedUser() {
		AuthenticatedUser authenticatedUser = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ((authentication != null) && (authentication.getDetails() instanceof AuthenticatedUser)) {
			authenticatedUser = (AuthenticatedUser) authentication.getDetails();
		}

		return authenticatedUser;
	}

}
