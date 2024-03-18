package br.com.amaral.service;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.com.amaral.constant.APITokens;
import br.com.amaral.model.dto.AsaasBankSlipRequestDTO;
import br.com.amaral.model.dto.AsaasCustomerDTO;
import br.com.amaral.util.ValidateCPF;

/**
 * Link https://docs.asaas.com/reference
 * 
 */
@Service
public class AsaasResourcesService implements Serializable {

	private static final long serialVersionUID = 1L;


	public String generateInvoiceKey() throws Exception {

		Client client = new HostIgnoringClient(APITokens.URL_ASAAS).hostIgnoringClient();
		WebResource webResource = client.resource(APITokens.URL_ASAAS + "pix/addressKeys");

		ClientResponse clientResponse = webResource.accept("application/json;charset=UTF-8")
				.header("Content-Type", "application/json").header("access_token", APITokens.TOKEN_ASAAS)
				.post(ClientResponse.class, "{\"type\":\"EVP\"}");

		String result = clientResponse.getEntity(String.class);
		clientResponse.close();

		return result;

	}

	public String searchCustomer(AsaasBankSlipRequestDTO bankSlipRequest) throws Exception {

		String customer_id = "";

		Client client = new HostIgnoringClient(APITokens.URL_ASAAS).hostIgnoringClient();
		WebResource webResource = client
				.resource(APITokens.URL_ASAAS + "customers?email=" + bankSlipRequest.getEmail());

		ClientResponse clientResponse = webResource.accept("application/json;charset=UTF-8")
				.header("Content-Type", "application/json").header("access_token", APITokens.TOKEN_ASAAS)
				.get(ClientResponse.class);

		LinkedHashMap<String, Object> parser = new JSONParser(clientResponse.getEntity(String.class)).parseObject();
		clientResponse.close();
		Integer total = Integer.parseInt(parser.get("totalCount").toString());

		if (total <= 0) {

			AsaasCustomerDTO customerDTO = new AsaasCustomerDTO();

			if (!ValidateCPF.isCPF(bankSlipRequest.getPayerCpfCnpj())) {
				customerDTO.setCpfCnpj("60051803046");
			} else {
				customerDTO.setCpfCnpj(bankSlipRequest.getPayerCpfCnpj());
			}

			customerDTO.setEmail(bankSlipRequest.getEmail());
			customerDTO.setName(bankSlipRequest.getPayerName());
			customerDTO.setPhone(bankSlipRequest.getPayerPhone());

			Client client2 = new HostIgnoringClient(APITokens.URL_ASAAS).hostIgnoringClient();
			WebResource webResource2 = client2.resource(APITokens.URL_ASAAS + "customers");

			ClientResponse clientResponse2 = webResource2.accept("application/json;charset=UTF-8")
					.header("Content-Type", "application/json").header("access_token", APITokens.TOKEN_ASAAS)
					.post(ClientResponse.class, new ObjectMapper().writeValueAsBytes(customerDTO));

			LinkedHashMap<String, Object> parser2 = new JSONParser(clientResponse2.getEntity(String.class))
					.parseObject();
			clientResponse2.close();

			customer_id = parser2.get("id").toString();

		} else {
			List<Object> data = (List<Object>) parser.get("data");
			customer_id = new Gson().toJsonTree(data.get(0)).getAsJsonObject().get("id").toString().replaceAll("\"",
					"");
		}

		return customer_id;

	}

}
