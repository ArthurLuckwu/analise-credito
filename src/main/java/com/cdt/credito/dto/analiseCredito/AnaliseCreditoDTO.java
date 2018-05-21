package com.cdt.credito.dto.analiseCredito;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "AnaliseCreditoParametros", description = "Parametros utilizados para realizar a análise de crédito")
public class AnaliseCreditoDTO {

	private boolean aprovado;
	
	private BigDecimal creditoConcedido;
	
}
