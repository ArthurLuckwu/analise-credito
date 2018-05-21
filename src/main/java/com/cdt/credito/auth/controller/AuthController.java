package com.cdt.credito.auth.controller;

import java.io.IOException;
import java.util.Set;

import javax.annotation.Resource;
import javax.security.auth.login.CredentialExpiredException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cdt.credito.annotations.AuthToken;
import com.cdt.credito.auth.dto.login.LoginDTO;
import com.cdt.credito.auth.dto.login.ResponseCaptchaDTO;
import com.cdt.credito.auth.dto.login.ResponseLoginDTO;
import com.cdt.credito.auth.enums.Permissao;
import com.cdt.credito.auth.model.Captcha;
import com.cdt.credito.auth.model.Usuario;
import com.cdt.credito.auth.service.CaptchaService;
import com.cdt.credito.auth.service.UsuarioService;
import com.cdt.credito.exceptions.InvalidCaptchaException;
import com.cdt.credito.utils.SessionManager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * Controller que fornece APIs para autenticação no sistema.
 * 
 * @author arthurluckwu
 *
 */
@Api(tags = "auth")
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Resource
	private CaptchaService captchaService;

	@Resource
	private UsuarioService usuarioService;

	/**
	 * Retorna um novo Captcha
	 * 
	 * @return ResponseCaptchaDTO - Dados do captcha.
	 * @throws IOException
	 */
	@ApiOperation(value = "Retorna um novo Captcha")
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseCaptchaDTO> captcha() throws IOException {
		Captcha captcha = this.captchaService.createCaptcha();

		System.out.println("Captcha: " + captcha.getValue());

		return ResponseEntity.ok().header("Cache-Control", "no-store", "no-cache")
				.body(new ResponseCaptchaDTO(captcha));
	}

	/**
	 * Realiza login do usuário no sistema.
	 * 
	 * @param request
	 * @param params
	 * @return ResponseLoginDTO - Objeto com dados do usuário logado.
	 * @throws BadCredentialsException
	 * @throws CredentialExpiredException
	 * @throws AccessDeniedException
	 * @throws InvalidCaptchaException
	 */
	@ApiOperation(value = "Realiza login do usuário no sistema", produces = "application/json")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<ResponseLoginDTO> login(HttpServletRequest request, @RequestBody @Valid LoginDTO params)
			throws BadCredentialsException, CredentialExpiredException, AccessDeniedException, InvalidCaptchaException {

		Usuario usuario = this.usuarioService.login(params.getEmail(), params.getSenha());
		Set<Permissao> permissoes = usuario.getPerfil().getPermissoes();
		
		if (!validateCaptcha(params.getIdCaptcha(), params.getValorCaptcha())) {
			throw new InvalidCaptchaException("Captcha inválido");
		}

		SessionManager.createSessionForUser(request, usuario, permissoes);

		ResponseLoginDTO loginResponse = new ResponseLoginDTO(usuario);

		return ResponseEntity.ok().body(loginResponse);
	}

	/**
	 * Realiza logout do usuário.
	 * 
	 * @param request
	 * @return void
	 */
	@ApiOperation(value = "Realiza logout do usuário")
	@AuthToken
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<Void> logout(HttpServletRequest request) {
		SessionManager.invalidateUserSession(request);

		return ResponseEntity.ok().build();
	}

	/**
	 * Função para validar captcha.
	 * 
	 * @param captchaId
	 * @param captchaResponse
	 * @return boolean - Se o captcha informado é válido ou não.
	 */
	private boolean validateCaptcha(String captchaId, final String captchaResponse) {
		boolean result = true;

		String storedCaptcha = this.captchaService.getCaptchaValueById(captchaId);
		
		if (storedCaptcha != null) {
			// Expires the captcha after the first use
			this.captchaService.invalidateCaptchaById(captchaId);
		}

		if (storedCaptcha == null || !storedCaptcha.equalsIgnoreCase(captchaResponse)) {
			result = false;
		}

		return result;
	}
}
