package com.cdt.credito.dto.portadorCredito;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "CriarAtualizarPortadorCreditoParametros", description = "Parametros utilizados para criar/atualizar um portador de crédito")
public class CreateUpdatePortadorCreditoDTO {

	@NotBlank
	@Size(min = 4, max = 80)
	@ApiModelProperty(value = "Nome do portador de crédito", required = true)
	private String nome;

	@NotBlank
	@Email
	@Size(max = 100)
	@ApiModelProperty(value = "Email do portador de crédito", required = true)
	private String email;

	@NotNull
	@Size(max = 11)
	private String cpf;
	
	@Size(max = 100)
	private String rua;

	@Size(max = 60)
	private String bairro;

	@Size(max = 6)
	private String numero;

	@Size(max = 100)
	private String complemento;

	@Size(max = 9)
	private String cep;

	@Size(max = 100)
	private String municipio;

	@Size(max = 2)
	private String uf;
	
	private BigDecimal dividaSerasa;
	
	private BigDecimal dividaSpc;
	
	private BigDecimal dividaCartorio;
	
}
