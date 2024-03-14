package br.com.amaral.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.amaral.model.dto.JunoBankSlipRequestDTO;
import br.com.amaral.service.JunoResourcesService;

@RestController
public class JunoController {
	
	@Autowired
	private JunoResourcesService junoResourcesService;

	@ResponseBody
	@PostMapping(value = "**/generate-charge")
	public ResponseEntity<String> generateCharge(@RequestBody @Valid JunoBankSlipRequestDTO bankSlipRequest) throws Exception{
		
		return  new ResponseEntity<>(junoResourcesService.generateCharge(bankSlipRequest), HttpStatus.OK);
	}
	
	
	@ResponseBody
	@PostMapping(value = "**/cancel-charge")
	public ResponseEntity<String> cancelCharge(@RequestBody @Valid String code) throws Exception{
		
		return new ResponseEntity<>(junoResourcesService.cancelChance(code), HttpStatus.OK);
	}
}
