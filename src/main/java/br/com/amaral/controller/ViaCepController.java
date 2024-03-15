package br.com.amaral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.amaral.model.dto.ViaCepDTO;
import br.com.amaral.service.ViaCepService;

/**
 * API Documentation: https://viacep.com.br/
 */
@RestController
public class ViaCepController {

	@Autowired
	private ViaCepService viaCepService;

	@ResponseBody
	@GetMapping(value = "**/get-address-via-cep/{cep}")
	public ResponseEntity<ViaCepDTO> getAddressViaCep(@PathVariable("cep") String cep) {

		return new ResponseEntity<>(viaCepService.getAddressViaCep(cep), HttpStatus.OK);

	}
}
