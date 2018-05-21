package com.cdt.credito.errorHandling;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component("accessDeniedHandler")
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.addHeader(ErrorCode.errorCodeKey(), ErrorCode.ACCESS_DENIED.getValue().toString());
		response.addHeader(ErrorCode.errorMessageKey(), accessDeniedException.getMessage());
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	}

}
