package com.cdt.credito.auth.dto.login;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel(value = "LoginParams", description = "Login operation parameters")
public class LoginDTO {

	@Email
	@Size(max = 100)
	@ApiModelProperty(value = "The user e-mail address", required = true)
	private String email;

	@Size(max = 32)
	@ApiModelProperty(value = "The user password", required = true)
	private String senha;

	@NotBlank
	@Size(max = 15)
	private String idCaptcha;

	@Size(max = 10)
	private String valorCaptcha;

}
