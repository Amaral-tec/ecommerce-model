package br.com.amaral.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.amaral.model.dto.ReceitaWsDTO;
import junit.framework.TestCase;

@Profile("test")
@SpringBootTest
class ReceitaWSTests extends TestCase {
	
	@Autowired
	private WebApplicationContext wac;
	 
    @Test
	void testRestApiGetByCEP() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		String cnpj = "06947283000160";
		
		ReceitaWsDTO entity = new ReceitaWsDTO();
		
		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.get("/get-entity-receitawsdto/" + cnpj)
				.content(objectMapper.writeValueAsString(entity)).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		ReceitaWsDTO returnEntity = objectMapper.readValue(returnApi.andReturn().getResponse().getContentAsString(),
				ReceitaWsDTO.class);

		assertEquals(cnpj, returnEntity.getCnpj().replaceAll("[^0-9]", ""));
	}
}