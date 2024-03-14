package br.com.amaral.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.amaral.model.dto.ViaCepDTO;

/**
 * API Documentation: https://viacep.com.br/
 */
@Service
public class ViaCepService {
	
	public ViaCepDTO getAddressViaCep(String cep) {
		return new RestTemplate().getForEntity("https://viacep.com.br/ws/" + cep + "/json/", ViaCepDTO.class).getBody();
	}
}
