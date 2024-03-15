package br.com.amaral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.amaral.model.dto.ReceitaWsDTO;
import br.com.amaral.service.ReceitaWsService;

/**
 * API Documentation:
 * https://developers.receitaws.com.br/#/operations/queryCNPJFree
 */
@RestController
public class ReceitaWSController {

	@Autowired
	private ReceitaWsService receitaWsService;

	@ResponseBody
	@GetMapping(value = "**/get-entity-receitawsdto/{cnpj}")
	public ResponseEntity<ReceitaWsDTO> getEntityReceitaWsDTO(@PathVariable("cnpj") String cnpj) {

		return new ResponseEntity<>(receitaWsService.getEntityReceitaWsDTO(cnpj), HttpStatus.OK);
	}
}
