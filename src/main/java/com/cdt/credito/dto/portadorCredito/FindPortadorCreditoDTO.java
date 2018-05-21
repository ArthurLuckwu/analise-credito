package com.cdt.credito.dto.portadorCredito;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel(value = "FindPortadorCreditoParams", description = "Parametros para buscar operador de credito")
public class FindPortadorCreditoDTO {

	@Size(max = 100)
	@ApiModelProperty(value = "Nome do portador de crédito")
	private String nome;

	@ApiModelProperty(value = "Cpf do portador de crédito")
	private String cpf;
	
	private Boolean aprovado;
	
	private Boolean analisado;

}
