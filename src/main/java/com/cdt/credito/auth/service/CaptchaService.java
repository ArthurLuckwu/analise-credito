package com.cdt.credito.auth.service;

import java.io.IOException;

import com.cdt.credito.auth.model.Captcha;

public interface CaptchaService {

	String getCaptchaValueById(String captchaId);
	
	void invalidateCaptchaById(String captchaId);

	Captcha createCaptcha(String text) throws IOException;
	
	Captcha createCaptcha() throws IOException;

	String createCaptchaText();

}