package br.com.amaral;

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

import br.com.amaral.model.PurchaseInvoice;
import br.com.amaral.model.AccountPayable;
import br.com.amaral.model.Individual;
import br.com.amaral.model.LegalEntity;
import br.com.amaral.repository.IPurchaseInvoiceRepository;
import br.com.amaral.repository.IAccountPayableRepository;
import br.com.amaral.repository.IIndividualRepository;
import br.com.amaral.repository.ILegalEntityRepository;
import br.com.amaral.service.RandomEntityGenerator;
import junit.framework.TestCase;

@Profile("test")
@SpringBootTest
class PurchaseInvoiceTests extends TestCase {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private IAccountPayableRepository accountPayableRepository;
	
	@Autowired
	private IIndividualRepository individualRepository;
	
	@Autowired
	private ILegalEntityRepository legalEntityRepository;
	
	@Autowired
	private IPurchaseInvoiceRepository entityRepository;

	@Test
	void testRestApiSave() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
	    MockMvc mockMvc = builder.build();
	    
	    PurchaseInvoice entity = createMockEntity();
	    
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    ResultActions returnApi = mockMvc
	    						 .perform(MockMvcRequestBuilders.post("/create-purchase-invoice")
	    						 .content(objectMapper.writeValueAsString(entity))
	    						 .accept(MediaType.APPLICATION_JSON)
	    						 .contentType(MediaType.APPLICATION_JSON));
	     
	    System.out.println("API Response: " + returnApi.andReturn().getResponse().getContentAsString());
	     
	    PurchaseInvoice returnEntity = objectMapper.readValue(returnApi.andReturn().getResponse().getContentAsString(),
	    		PurchaseInvoice.class);
	    
	    assertEquals(entity.getNumber(), returnEntity.getNumber());
	    
	    deleteMockEntity(returnEntity);
	}

	@Test
	void testRestApiDelete() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		PurchaseInvoice entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc
				.perform(MockMvcRequestBuilders.post("/delete-purchase-invoice").content(objectMapper.writeValueAsString(entity))
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

		PurchaseInvoice entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders
				.delete("/delete-purchase-invoice-by-id/" + entity.getId()).content(objectMapper.writeValueAsString(entity))
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

		PurchaseInvoice entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.get("/get-purchase-invoice/" + entity.getId())
				.content(objectMapper.writeValueAsString(entity)).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		PurchaseInvoice returnEntity = objectMapper.readValue(returnApi.andReturn().getResponse().getContentAsString(),
				PurchaseInvoice.class);

		assertEquals(entity.getId(), returnEntity.getId());

		deleteMockEntity(entity);
	}
	
	@Test
	void testRestApiGetByPerson() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		PurchaseInvoice entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders
				.get("/find-purchase-invoice-by-person/" + entity.getIndividual().getId()).content(objectMapper.writeValueAsString(entity))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		deleteMockEntity(entity);
	}
	
	@Test
	void testRestApiGetByLegalEntity() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		PurchaseInvoice entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders
				.get("/find-purchase-invoice-by-legal-entity/" + entity.getLegalEntity().getId()).content(objectMapper.writeValueAsString(entity))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		deleteMockEntity(entity);
	}
	
	@Test
	void testRestApiGetByPurchaseInvoice() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		PurchaseInvoice entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders
				.get("/find-purchase-invoice-by-account-payable/" + entity.getAccountPayable().getId()).content(objectMapper.writeValueAsString(entity))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		deleteMockEntity(entity);
	}
	
	@Test
	void testRestApiGetByNumber() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		PurchaseInvoice entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders
				.get("/find-purchase-invoice-by-number/" + entity.getNumber()).content(objectMapper.writeValueAsString(entity))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		deleteMockEntity(entity);
	}
	
	private PurchaseInvoice createMockEntity() {

		Individual individual = RandomEntityGenerator.generateIndividual();
	    individualRepository.save(individual);
	    
	    LegalEntity legalEntity = RandomEntityGenerator.generateLegalEntity();
	    legalEntityRepository.save(legalEntity);
	    
	    AccountPayable accountPayable = RandomEntityGenerator.createAccountPayable();
	    accountPayable.setIndividual(individual);
	    accountPayable.setSupplier(legalEntity);
	    accountPayable.setLegalEntity(legalEntity);
	    accountPayableRepository.save(accountPayable);
	    
	    PurchaseInvoice entity = RandomEntityGenerator.createPurchaseInvoice();
	    entity.setIndividual(individual);
	    entity.setLegalEntity(legalEntity);
	    entity.setAccountPayable(accountPayable);

		return entity;
	}

	private void deleteMockEntity(PurchaseInvoice entity) {
		
		entityRepository.deleteById(entity.getId());
		accountPayableRepository.deleteById(entity.getAccountPayable().getId());
		individualRepository.deleteById(entity.getIndividual().getId());
		legalEntityRepository.deleteById(entity.getLegalEntity().getId());
	}
}
