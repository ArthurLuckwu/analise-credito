package com.cdt.credito.dto.analiseCredito;

import java.math.BigDecimal;

import com.cdt.credito.dto.portadorCredito.ResponsePortadorCreditoDTO;
import com.cdt.credito.model.Credito;
import com.cdt.credito.model.PortadorCredito;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel(value = "CreditoResponse", description = "Objeto de Crédito")
public class ResponseAnaliseCreditoDTO {

	@ApiModelProperty(value = "Id do crédito")
	private Long id;

	@ApiModelProperty(value = "Valor do crédito concedido")
	private BigDecimal creditoConcedido;

	@ApiModelProperty(value = "Dados do portador de crédito")
	private ResponsePortadorCreditoDTO portadorCredito;
	
	public ResponseAnaliseCreditoDTO(Credito credito) {
		this.id = credito.getId();
		this.creditoConcedido = credito.getCreditoConcedido();
		this.portadorCredito = new ResponsePortadorCreditoDTO(credito.getPortadorCredito());
	}
	
	public ResponseAnaliseCreditoDTO(PortadorCredito portador) {
		this.portadorCredito = new ResponsePortadorCreditoDTO(portador);
	}
}
