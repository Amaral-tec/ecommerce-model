package br.com.amaral.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.amaral.model.dto.ReceitaWsDTO;

/**
 * API Documentation: https://developers.receitaws.com.br/#/operations/queryCNPJFree
 */
@Service
public class ReceitaWsService {
	
	public ReceitaWsDTO entityReceitaWsDTO(String cnpj) {
		return new RestTemplate().getForEntity("https://receitaws.com.br/v1/cnpj/" + cnpj, ReceitaWsDTO.class).getBody();
	}

}
