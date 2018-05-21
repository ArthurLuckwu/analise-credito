package com.cdt.credito.auth.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Modelo de Captcha que será utilizado para autenticação no sistema.
 * 
 * @author arthurluckwu
 *
 */
@Data
@EqualsAndHashCode(of = "id")
public class Captcha {

	private String id;
	private String value;
	private String imageData;

	public Captcha(String id, String value, String imageData) {
		super();
		this.id = id;
		this.value = value;
		this.imageData = imageData;
	}

}
