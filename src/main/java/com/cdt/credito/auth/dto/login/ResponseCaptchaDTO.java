package com.cdt.credito.auth.dto.login;

import com.cdt.credito.auth.model.Captcha;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCaptchaDTO {

	private String id;
	private String imageData;
	
	public ResponseCaptchaDTO(Captcha captcha) {
		this.id = captcha.getId();
		this.imageData = captcha.getImageData();
	}

}
