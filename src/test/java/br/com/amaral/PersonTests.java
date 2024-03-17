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

import br.com.amaral.model.Individual;
import br.com.amaral.model.LegalEntity;
import br.com.amaral.repository.IIndividualRepository;
import br.com.amaral.repository.ILegalEntityRepository;
import br.com.amaral.service.RandomEntityGenerator;
import junit.framework.TestCase;

@Profile("test")
@SpringBootTest
class PersonTests extends TestCase {

	@Autowired
	private IIndividualRepository individualRepository;
	
	@Autowired
	private ILegalEntityRepository legalEntityRepository;
	
	@Autowired
	private WebApplicationContext wac;

	@Test
	void testLegalEntitySave() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
	    MockMvc mockMvc = builder.build();
	    
	    LegalEntity entity = RandomEntityGenerator.generateLegalEntity();
	    
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    ResultActions returnApi = mockMvc
	    						 .perform(MockMvcRequestBuilders.post("/create-legal-entity")
	    						 .content(objectMapper.writeValueAsString(entity))
	    						 .accept(MediaType.APPLICATION_JSON)
	    						 .contentType(MediaType.APPLICATION_JSON));
	     
	    System.out.println("API Response: " + returnApi.andReturn().getResponse().getContentAsString());
	     
	    LegalEntity returnEntity = objectMapper.readValue(returnApi.andReturn().getResponse().getContentAsString(),
	    		LegalEntity.class);
	    
	    assertEquals(entity.getName(), returnEntity.getName());
	    
	    legalEntityRepository.deleteById(returnEntity.getId());
	}
	
	@Test
	void testIndividualSave() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
	    MockMvc mockMvc = builder.build();
	    
	    Individual entity = RandomEntityGenerator.generateIndividual();
	    
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    ResultActions returnApi = mockMvc
	    						 .perform(MockMvcRequestBuilders.post("/create-individual")
	    						 .content(objectMapper.writeValueAsString(entity))
	    						 .accept(MediaType.APPLICATION_JSON)
	    						 .contentType(MediaType.APPLICATION_JSON));
	     
	    System.out.println("API Response: " + returnApi.andReturn().getResponse().getContentAsString());
	     
	    Individual returnEntity = objectMapper.readValue(returnApi.andReturn().getResponse().getContentAsString(),
	    		Individual.class);
	    
	    assertEquals(entity.getName(), returnEntity.getName());
	    
	}

	@Test
	void testLegalEntityDelete() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		LegalEntity entity = RandomEntityGenerator.generateLegalEntity();
		legalEntityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc
				.perform(MockMvcRequestBuilders.post("/delete-legal-entity").content(objectMapper.writeValueAsString(entity))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		System.out.println("API Response: " + returnApi.andReturn().getResponse().getContentAsString());
		System.out.println("API Status: " + returnApi.andReturn().getResponse().getStatus());

		assertEquals("OK: Deletion completed successfully.", returnApi.andReturn().getResponse().getContentAsString());
		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		legalEntityRepository.deleteById(entity.getId());
	}
	
	@Test
	void testIndividualDelete() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Individual entity = RandomEntityGenerator.generateIndividual();
		individualRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc
				.perform(MockMvcRequestBuilders.post("/delete-individual").content(objectMapper.writeValueAsString(entity))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		System.out.println("API Response: " + returnApi.andReturn().getResponse().getContentAsString());
		System.out.println("API Status: " + returnApi.andReturn().getResponse().getStatus());

		assertEquals("OK: Deletion completed successfully.", returnApi.andReturn().getResponse().getContentAsString());
		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		individualRepository.deleteById(entity.getId());
	}

	@Test
	void testLegalEntityDeleteById() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		LegalEntity entity = RandomEntityGenerator.generateLegalEntity();
		legalEntityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders
				.delete("/delete-legal-entity-by-id/" + entity.getId()).content(objectMapper.writeValueAsString(entity))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		System.out.println("API Response: " + returnApi.andReturn().getResponse().getContentAsString());
		System.out.println("API Status: " + returnApi.andReturn().getResponse().getStatus());

		assertEquals("OK: Deletion completed successfully.", returnApi.andReturn().getResponse().getContentAsString());
		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		legalEntityRepository.deleteById(entity.getId());
	}
	
	@Test
	void testIndividualDeleteById() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Individual entity = RandomEntityGenerator.generateIndividual();
		individualRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders
				.delete("/delete-individual-by-id/" + entity.getId()).content(objectMapper.writeValueAsString(entity))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		System.out.println("API Response: " + returnApi.andReturn().getResponse().getContentAsString());
		System.out.println("API Status: " + returnApi.andReturn().getResponse().getStatus());

		assertEquals("OK: Deletion completed successfully.", returnApi.andReturn().getResponse().getContentAsString());
		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		individualRepository.deleteById(entity.getId());
	}

	@Test
	void testLegalEntityGetById() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		LegalEntity entity = RandomEntityGenerator.generateLegalEntity();
		legalEntityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.get("/get-legal-entity/" + entity.getId())
				.content(objectMapper.writeValueAsString(entity)).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		LegalEntity returnEntity = objectMapper.readValue(returnApi.andReturn().getResponse().getContentAsString(),
				LegalEntity.class);

		assertEquals(entity.getId(), returnEntity.getId());

		legalEntityRepository.deleteById(entity.getId());
	}
	
	@Test
	void testIndividualGetById() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Individual entity = RandomEntityGenerator.generateIndividual();
		individualRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.get("/get-individual/" + entity.getId())
				.content(objectMapper.writeValueAsString(entity)).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		Individual returnEntity = objectMapper.readValue(returnApi.andReturn().getResponse().getContentAsString(),
				Individual.class);

		assertEquals(entity.getId(), returnEntity.getId());

		individualRepository.deleteById(entity.getId());
	}
	
	@Test
	void testLegalEntityGetByCNPJ() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		LegalEntity entity = RandomEntityGenerator.generateLegalEntity();
		legalEntityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.get("/get-legal-entity-by-cnpj/" + entity.getCnpj())
				.content(objectMapper.writeValueAsString(entity)).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		LegalEntity returnEntity = objectMapper.readValue(returnApi.andReturn().getResponse().getContentAsString(),
				LegalEntity.class);

		assertEquals(entity.getId(), returnEntity.getId());

		legalEntityRepository.deleteById(entity.getId());
	}
	
	@Test
	void testIndividualGetByCPF() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Individual entity = RandomEntityGenerator.generateIndividual();
		individualRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.get("/get-individual-by-cpf/" + entity.getCpf())
				.content(objectMapper.writeValueAsString(entity)).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		Individual returnEntity = objectMapper.readValue(returnApi.andReturn().getResponse().getContentAsString(),
				Individual.class);

		assertEquals(entity.getId(), returnEntity.getId());

		individualRepository.deleteById(entity.getId());
	}
	
	@Test
	void testLegalEntityFindAll() throws JsonProcessingException, Exception {

	    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
	    MockMvc mockMvc = builder.build();

	    List<LegalEntity> entityList = new ArrayList<>();
	    entityList.add(RandomEntityGenerator.generateLegalEntity());
	    entityList.add(RandomEntityGenerator.generateLegalEntity());

	    legalEntityRepository.saveAll(entityList);

	    ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.get("/find-all-legal-entity")
	            .accept(MediaType.APPLICATION_JSON)
	            .contentType(MediaType.APPLICATION_JSON));

	    assertEquals(200, returnApi.andReturn().getResponse().getStatus());

	    legalEntityRepository.deleteAll(entityList);
	}
	
	@Test
	void testIndividualFindAll() throws JsonProcessingException, Exception {

	    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
	    MockMvc mockMvc = builder.build();

	    List<Individual> entityList = new ArrayList<>();
	    entityList.add(RandomEntityGenerator.generateIndividual());
	    entityList.add(RandomEntityGenerator.generateIndividual());

	    individualRepository.saveAll(entityList);

	    ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.get("/find-all-individual")
	            .accept(MediaType.APPLICATION_JSON)
	            .contentType(MediaType.APPLICATION_JSON));

	    assertEquals(200, returnApi.andReturn().getResponse().getStatus());

	    individualRepository.deleteAll(entityList);
	}

	@Test
	void testLegalEntityGetByDescription() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		LegalEntity entity = RandomEntityGenerator.generateLegalEntity();
		legalEntityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders
				.get("/find-legal-entity-by-name/" + entity.getName()).content(objectMapper.writeValueAsString(entity))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		legalEntityRepository.deleteById(entity.getId());
	}
	
	@Test
	void testIndividualGetByDescription() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Individual entity = RandomEntityGenerator.generateIndividual();
		individualRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders
				.get("/find-individual-by-name/" + entity.getName()).content(objectMapper.writeValueAsString(entity))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		individualRepository.deleteById(entity.getId());
	}
}
