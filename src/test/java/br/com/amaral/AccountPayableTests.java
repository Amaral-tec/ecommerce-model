package br.com.amaral;

import java.util.ArrayList;
import java.util.List;

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

import br.com.amaral.model.AccountPayable;
import br.com.amaral.model.Individual;
import br.com.amaral.model.LegalEntity;
import br.com.amaral.repository.IAccountPayableRepository;
import br.com.amaral.repository.IIndividualRepository;
import br.com.amaral.repository.ILegalEntityRepository;
import br.com.amaral.service.RandomEntityGenerator;
import junit.framework.TestCase;

@Profile("test")
@SpringBootTest
class AccountPayableTests extends TestCase {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private IAccountPayableRepository entityRepository;
		
	@Autowired
	private IIndividualRepository individualRepository;
	
	@Autowired
	private ILegalEntityRepository legalEntityRepository;

	@Test
	void testRestApiSave() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
	    MockMvc mockMvc = builder.build();
	    
	    AccountPayable entity = createMockEntity();
	    
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    ResultActions returnApi = mockMvc
	    						 .perform(MockMvcRequestBuilders.post("/create-account-payable")
	    						 .content(objectMapper.writeValueAsString(entity))
	    						 .accept(MediaType.APPLICATION_JSON)
	    						 .contentType(MediaType.APPLICATION_JSON));
	     
	    System.out.println("API Response: " + returnApi.andReturn().getResponse().getContentAsString());
	     
	    AccountPayable returnEntity = objectMapper.readValue(returnApi.andReturn().getResponse().getContentAsString(),
	    		AccountPayable.class);
	    
	    assertEquals(entity.getDescription(), returnEntity.getDescription());
	    
	    deleteMockEntity(returnEntity);
	}

	@Test
	void testRestApiDelete() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		AccountPayable entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc
				.perform(MockMvcRequestBuilders.post("/delete-account-payable").content(objectMapper.writeValueAsString(entity))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		System.out.println("API Response: " + returnApi.andReturn().getResponse().getContentAsString());
		System.out.println("API Status: " + returnApi.andReturn().getResponse().getStatus());

		assertEquals("OK: Deletion completed successfully.", returnApi.andReturn().getResponse().getContentAsString());
		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		deleteMockEntity(entity);
	}

	@Test
	void testRestApiDeleteById() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		AccountPayable entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders
				.delete("/delete-account-payable-by-id/" + entity.getId()).content(objectMapper.writeValueAsString(entity))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		System.out.println("API Response: " + returnApi.andReturn().getResponse().getContentAsString());
		System.out.println("API Status: " + returnApi.andReturn().getResponse().getStatus());

		assertEquals("OK: Deletion completed successfully.", returnApi.andReturn().getResponse().getContentAsString());
		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		deleteMockEntity(entity);
	}

	@Test
	void testRestApiGetById() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		AccountPayable entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.get("/get-account-payable/" + entity.getId())
				.content(objectMapper.writeValueAsString(entity)).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		AccountPayable returnEntity = objectMapper.readValue(returnApi.andReturn().getResponse().getContentAsString(),
				AccountPayable.class);

		assertEquals(entity.getId(), returnEntity.getId());

		deleteMockEntity(entity);
	}
	
	@Test
	void testRestApiFindAll() throws JsonProcessingException, Exception {

	    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
	    MockMvc mockMvc = builder.build();
	    
	    List<AccountPayable> entityList = new ArrayList<>();
	    for (int i = 0; i < 2; i++) {
	    	AccountPayable entity = createMockEntity();
	        entityList.add(entityRepository.save(entity));
	    }

	    ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.get("/find-all-account-payable")
	            .accept(MediaType.APPLICATION_JSON)
	            .contentType(MediaType.APPLICATION_JSON));

	    assertEquals(200, returnApi.andReturn().getResponse().getStatus());

	    for (AccountPayable entity : entityList) {
	    	deleteMockEntity(entity);
	    }
	    
	}

	@Test
	void testRestApiGetByDescription() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		AccountPayable entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders
				.get("/find-account-payable-by-name/" + entity.getDescription()).content(objectMapper.writeValueAsString(entity))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		deleteMockEntity(entity);
	}
	
	@Test
	void testRestApiGetByPerson() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		AccountPayable entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders
				.get("/find-account-payable-by-person/" + entity.getIndividual().getId()).content(objectMapper.writeValueAsString(entity))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		deleteMockEntity(entity);
	}
	
	@Test
	void testRestApiGetBySupplier() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		AccountPayable entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders
				.get("/find-account-payable-by-supplier/" + entity.getSupplier().getId()).content(objectMapper.writeValueAsString(entity))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		deleteMockEntity(entity);
	}
	
	@Test
	void testRestApiGetByLegalEntity() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		AccountPayable entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders
				.get("/find-account-payable-by-legal-entity/" + entity.getLegalEntity().getId()).content(objectMapper.writeValueAsString(entity))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		deleteMockEntity(entity);
	}
	
	private AccountPayable createMockEntity() {

		Individual individual = RandomEntityGenerator.generateIndividual();
	    individualRepository.save(individual);
	    
	    LegalEntity legalEntity = RandomEntityGenerator.generateLegalEntity();
	    legalEntityRepository.save(legalEntity);
	    
	    AccountPayable entity = RandomEntityGenerator.createAccountPayable();
	    entity.setIndividual(individual);
	    entity.setSupplier(legalEntity);
	    entity.setLegalEntity(legalEntity);

		return entity;
	}

	private void deleteMockEntity(AccountPayable entity) {
		
		entityRepository.deleteById(entity.getId());
		individualRepository.deleteById(entity.getIndividual().getId());
		legalEntityRepository.deleteById(entity.getLegalEntity().getId());
	}
}
