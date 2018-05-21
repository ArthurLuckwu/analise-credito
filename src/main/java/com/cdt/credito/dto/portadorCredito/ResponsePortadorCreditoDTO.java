package com.cdt.credito.dto.portadorCredito;

import java.math.BigDecimal;

import com.cdt.credito.model.PortadorCredito;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel(value = "PortadorCreditoResponse", description = "Objeto Portador de Crédito")
public class ResponsePortadorCreditoDTO {

	@ApiModelProperty(value = "Id do portador de crédito")
	private Long id;

	@ApiModelProperty(value = "Nome do portador de crédito")
	private String nome;

	@ApiModelProperty(value = "Email do portador de crédito")
	private String email;
	
	private String cpf;
	private String rua;
	private String numero;
	private String complemento;
	private String cep;
	private String uf;
	private String municipio;
	private String bairro;
	private BigDecimal dividaSerasa;
	private BigDecimal dividaSpc;
	private BigDecimal dividaCartorio;
	private Boolean aprovado;
	private Boolean analisado;

	public ResponsePortadorCreditoDTO(PortadorCredito portador) {
		this.id = portador.getId();
		this.nome = portador.getNome();
		this.email = portador.getEmail();
		this.cpf = portador.getCpf();
		this.rua = portador.getRua();
		this.numero = portador.getNumero();
		this.complemento = portador.getComplemento();
		this.cep = portador.getCep();
		this.uf = portador.getUf();
		this.municipio = portador.getMunicipio();
		this.bairro = portador.getBairro();
		this.dividaSerasa = portador.getDividaSerasa();
		this.dividaSpc = portador.getDividaSpc();
		this.dividaCartorio = portador.getDividaCartorio();
		this.aprovado = portador.getAprovado();
		this.analisado = portador.getAnalisado();
				
	}
}
