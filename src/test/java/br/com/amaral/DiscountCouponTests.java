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

import br.com.amaral.model.DiscountCoupon;
import br.com.amaral.model.LegalEntity;
import br.com.amaral.repository.IDiscountCouponRepository;
import br.com.amaral.repository.ILegalEntityRepository;
import br.com.amaral.service.RandomEntityGenerator;
import junit.framework.TestCase;

@Profile("test")
@SpringBootTest
class DiscountCouponTests extends TestCase {

	@Autowired
	private IDiscountCouponRepository entityRepository;
		
	@Autowired
	private ILegalEntityRepository legalEntityRepository;
	
	@Autowired
	private WebApplicationContext wac;

	@Test
	void testRestApiSave() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
	    MockMvc mockMvc = builder.build();
	   
	    DiscountCoupon entity = createMockEntity();
	    
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    ResultActions returnApi = mockMvc
	    						 .perform(MockMvcRequestBuilders.post("/create-discount-coupon")
	    						 .content(objectMapper.writeValueAsString(entity))
	    						 .accept(MediaType.APPLICATION_JSON)
	    						 .contentType(MediaType.APPLICATION_JSON));
	     
	    System.out.println("API Response: " + returnApi.andReturn().getResponse().getContentAsString());
	     
	    DiscountCoupon returnEntity = objectMapper.readValue(returnApi.andReturn().getResponse().getContentAsString(),
	    		DiscountCoupon.class);
	    
	    assertEquals(entity.getCode(), returnEntity.getCode());
	    
	    deleteMockEntity(returnEntity);
	}

	@Test
	void testRestApiDelete() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		DiscountCoupon entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc
				.perform(MockMvcRequestBuilders.post("/delete-discount-coupon").content(objectMapper.writeValueAsString(entity))
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

		DiscountCoupon entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders
				.delete("/delete-discount-coupon-by-id/" + entity.getId()).content(objectMapper.writeValueAsString(entity))
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

		DiscountCoupon entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.get("/get-discount-coupon/" + entity.getId())
				.content(objectMapper.writeValueAsString(entity)).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		DiscountCoupon returnEntity = objectMapper.readValue(returnApi.andReturn().getResponse().getContentAsString(),
				DiscountCoupon.class);

		assertEquals(entity.getId(), returnEntity.getId());

		deleteMockEntity(entity);
	}
	
	@Test
	void testRestApiFindAll() throws JsonProcessingException, Exception {

	    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
	    MockMvc mockMvc = builder.build();
	    
	    List<DiscountCoupon> entityList = new ArrayList<>();
	    for (int i = 0; i < 2; i++) {
	        DiscountCoupon entity = createMockEntity();
	        entityList.add(entityRepository.save(entity));
	    }

	    ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.get("/find-all-discount-coupon")
	            .accept(MediaType.APPLICATION_JSON)
	            .contentType(MediaType.APPLICATION_JSON));

	    assertEquals(200, returnApi.andReturn().getResponse().getStatus());

	    for (DiscountCoupon entity : entityList) {
	    	deleteMockEntity(entity);
	    }	    
	}

	@Test
	void testRestApiGetByDescription() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		DiscountCoupon entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders
				.get("/find-discount-coupon-by-name/" + entity.getCode()).content(objectMapper.writeValueAsString(entity))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		deleteMockEntity(entity);
	}
	
	private DiscountCoupon createMockEntity() {
 
	    LegalEntity legalEntity = RandomEntityGenerator.generateLegalEntity();
	    legalEntityRepository.save(legalEntity);
	    
	    DiscountCoupon entity = RandomEntityGenerator.createDiscountCoupon();
	    entity.setLegalEntity(legalEntity);

		return entity;
	}

	private void deleteMockEntity(DiscountCoupon entity) {
		
		entityRepository.deleteById(entity.getId());
		legalEntityRepository.deleteById(entity.getLegalEntity().getId());
	}
}
