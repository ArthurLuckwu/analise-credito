package com.cdt.credito.errorHandling;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component("unauthorizedEntryPoint")
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		if (request.getHeader("x-auth-token") != null) {
			response.addHeader(ErrorCode.errorCodeKey(), ErrorCode.SESSION_EXPIRED.getValue().toString());
			response.addHeader(ErrorCode.errorMessageKey(), "Sua sessão expirou");
		} else {
			response.addHeader(ErrorCode.errorCodeKey(), ErrorCode.UNAUTHORIZED.getValue().toString());
			response.addHeader(ErrorCode.errorMessageKey(), authException.getMessage());
		}
	}

}
