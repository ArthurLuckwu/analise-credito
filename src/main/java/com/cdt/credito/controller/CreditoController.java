package com.cdt.credito.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cdt.credito.annotations.AuthToken;
import com.cdt.credito.dto.analiseCredito.AnaliseCreditoDTO;
import com.cdt.credito.dto.analiseCredito.ResponseAnaliseCreditoDTO;
import com.cdt.credito.dto.portadorCredito.CreateUpdatePortadorCreditoDTO;
import com.cdt.credito.dto.portadorCredito.FindPortadorCreditoDTO;
import com.cdt.credito.dto.portadorCredito.ResponsePortadorCreditoDTO;
import com.cdt.credito.exceptions.DuplicatedRecordException;
import com.cdt.credito.exceptions.RecordNotFoundException;
import com.cdt.credito.model.Credito;
import com.cdt.credito.model.PortadorCredito;
import com.cdt.credito.service.CreditoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
/**
 * Controller que fornece APIs referentes às operações sobre Operadores e Análise de Crédito
 * 
 * @author arthurluckwu
 *
 */
@Api(tags = "credito")
@RestController
@RequestMapping("/credito")
public class CreditoController {
	
	@Resource
	private CreditoService creditoService;

	/**
	 * Cadastrar Portador de Crédito
	 * 
	 * @param params
	 * @return ResponsePortadorCreditoDTO - Objeto contendo dados do portador de crédito cadastrado.
	 * @throws DuplicatedRecordException
	 * @throws RecordNotFoundException
	 * @throws IOException
	 */
	@ApiOperation(value = "Cadastrar Portador de Crédito", produces = "application/json")
	@AuthToken
	@ApiImplicitParams(@ApiImplicitParam(name = "x-auth-token", required = true, dataType = "string", paramType = "header", value = "Token de Autenticação obtido ao fazer login"))
	@RequestMapping(method = RequestMethod.POST, value="/cadastrarPortadorCredito")
	public @ResponseBody ResponseEntity<ResponsePortadorCreditoDTO> create(
			@RequestBody @Valid CreateUpdatePortadorCreditoDTO params) throws DuplicatedRecordException, RecordNotFoundException, IOException {
		PortadorCredito portador = new PortadorCredito();
		BeanUtils.copyProperties(params, portador);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponsePortadorCreditoDTO(this.creditoService.createPortadorCredito(portador)));
	}
	
	/**
	 * Buscar Portadores de Crédito
	 * 
	 * @param params
	 * @param pageable
	 * @return ResponsePortadorCreditoDTO - Page contendo dados dos Portadores de Crédito retornados.
	 * @throws RecordNotFoundException
	 */
	@ApiOperation(value = "Buscar Portadores de Crédito", produces = "application/json")
	@AuthToken
	@ApiImplicitParams(@ApiImplicitParam(name = "x-auth-token", required = true, dataType = "string", paramType = "header", value = "Token de Autenticação obtido ao fazer login"))
	@RequestMapping(value = "/listPortador", method = RequestMethod.POST)
	public ResponseEntity<Page<ResponsePortadorCreditoDTO>> listPortador(@RequestBody(required = false) @Valid FindPortadorCreditoDTO params,
			@ApiIgnore Pageable pageable) throws RecordNotFoundException {
		Page<PortadorCredito> portadorPage = this.creditoService.findPortadorCredito(params, pageable);

		Page<ResponsePortadorCreditoDTO> resultDto = new PageImpl<>(convertToDto(portadorPage.getContent()), pageable,
				portadorPage.getTotalElements());

		return ResponseEntity.ok().body(resultDto);
	}
	
	/**
	 * Responsável por fazer a Análise do crédito.
	 * 
	 * @param id
	 * @param params
	 * @return void
	 * @throws DuplicatedRecordException
	 * @throws RecordNotFoundException
	 * @throws IOException
	 */
	@ApiOperation(value = "Analisar Crédito", produces = "application/json")
	@AuthToken
	@ApiImplicitParams(@ApiImplicitParam(name = "x-auth-token", required = true, dataType = "string", paramType = "header", value = "Token de Autenticação obtido ao fazer login"))
	@RequestMapping(method = RequestMethod.PUT, value="/analisarCredito/{id}")
	public ResponseEntity<Void> analisarCredito(@PathVariable Long id,
			@RequestBody @Valid AnaliseCreditoDTO params) throws DuplicatedRecordException, RecordNotFoundException, IOException {
			
		this.creditoService.analisarCredito(params, id);
		
		return ResponseEntity.ok().build();

	}
	
	/**
	 * Retorna dados de um Portador de Crédito em específico.
	 * 
	 * @param id 
	 * @return ResponsePortadorCreditoDTO - Dados do portador de crédito em específico.
	 * @throws RecordNotFoundException
	 */
	@ApiOperation(value = "Retorna dados de um Portador de Crédito em específico", produces = "application/json")
	@AuthToken
	@ApiImplicitParams(@ApiImplicitParam(name = "x-auth-token", required = true, dataType = "string", paramType = "header", value = "Token de Autenticação obtido ao fazer login"))
	@RequestMapping(method = RequestMethod.GET, value = "/portador/{id}")
	public @ResponseBody ResponseEntity<ResponsePortadorCreditoDTO> getPortador(@PathVariable Long id) throws RecordNotFoundException {
		return ResponseEntity.ok(new ResponsePortadorCreditoDTO(this.creditoService.getPortadorCredito(id)));
	}
	
	/**
	 * Retorna dados de uma Análise de Crédito em específico
	 * 
	 * @param id
	 * @return ResponseAnaliseCreditoDTO - Dados de uma análise de crédito em específico.
	 * @throws RecordNotFoundException
	 */
	@ApiOperation(value = "Retorna dados de uma Análise de Crédito em específico", produces = "application/json")
	@AuthToken
	@ApiImplicitParams(@ApiImplicitParam(name = "x-auth-token", required = true, dataType = "string", paramType = "header", value = "Token de Autenticação obtido ao fazer login"))
	@RequestMapping(method = RequestMethod.GET, value = "/analise/{id}")
	public @ResponseBody ResponseEntity<ResponseAnaliseCreditoDTO> getAnalise(@PathVariable Long id) throws RecordNotFoundException {
		Credito credito = this.creditoService.getAnaliseCredito(id);
		if (credito.getId() == null) {
			return ResponseEntity.ok(new ResponseAnaliseCreditoDTO(credito.getPortadorCredito())); 
		} 
		
		return ResponseEntity.ok(new ResponseAnaliseCreditoDTO(this.creditoService.getAnaliseCredito(id)));
	}
	
	/**
	 * Transforma uma lista de objetos PortadorCredito em uma lista de DTOs.
	 * 
	 * @param portadorList
	 * @return ResponsePortadorCreditoDTO - Lista de DTOs com dados de portadores de crédito.  
	 */
	private List<ResponsePortadorCreditoDTO> convertToDto(List<PortadorCredito> portadorList) {
		List<ResponsePortadorCreditoDTO> listPortadorDto = new ArrayList<>();
		for (PortadorCredito portador : portadorList) {
			listPortadorDto.add(new ResponsePortadorCreditoDTO(portador));
		}
		return listPortadorDto;
	}
}
