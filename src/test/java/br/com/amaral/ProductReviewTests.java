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

import br.com.amaral.model.Product;
import br.com.amaral.model.ProductBrand;
import br.com.amaral.model.ProductCategory;
import br.com.amaral.model.ProductReview;
import br.com.amaral.model.Individual;
import br.com.amaral.model.LegalEntity;
import br.com.amaral.repository.IProductRepository;
import br.com.amaral.repository.IIndividualRepository;
import br.com.amaral.repository.ILegalEntityRepository;
import br.com.amaral.repository.IProductBrandRepository;
import br.com.amaral.repository.IProductCategoryRepository;
import br.com.amaral.repository.IProductReviewRepository;
import br.com.amaral.service.RandomEntityGenerator;
import junit.framework.TestCase;

@Profile("test")
@SpringBootTest
class ProductReviewTests extends TestCase {

	@Autowired
	private IIndividualRepository individualRepository;
	
	@Autowired
	private ILegalEntityRepository legalEntityRepository;
	
	@Autowired
	private IProductReviewRepository entityRepository;

	@Autowired
	private IProductBrandRepository productBrandRepository;

	@Autowired
	private IProductCategoryRepository productCategoryRepository;

	@Autowired
	private IProductRepository productRepository;

	@Autowired
	private WebApplicationContext wac;

	@Test
	void testRestApiSave() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		ProductReview entity = createMockEntity();

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(
				MockMvcRequestBuilders.post("/create-product-review").content(objectMapper.writeValueAsString(entity))
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		System.out.println("API Response: " + returnApi.andReturn().getResponse().getContentAsString());

		ProductReview returnEntity = objectMapper.readValue(returnApi.andReturn().getResponse().getContentAsString(),
				ProductReview.class);

		assertEquals(entity.getDescription(), returnEntity.getDescription());

		deleteMockEntity(returnEntity);
	}

	@Test
	void testRestApiDelete() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		ProductReview entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(
				MockMvcRequestBuilders.post("/delete-product-review").content(objectMapper.writeValueAsString(entity))
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

		ProductReview entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc
				.perform(MockMvcRequestBuilders.delete("/delete-product-review-by-id/" + entity.getId())
						.content(objectMapper.writeValueAsString(entity)).accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON));

		System.out.println("API Response: " + returnApi.andReturn().getResponse().getContentAsString());
		System.out.println("API Status: " + returnApi.andReturn().getResponse().getStatus());

		assertEquals("OK: Deletion completed successfully.", returnApi.andReturn().getResponse().getContentAsString());
		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		deleteMockEntity(entity);
	}
	
	@Test
	void testRestApiGetByProduct() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		ProductReview entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.get("/find-product-review-by-product/" + entity.getProduct().getId())
				.content(objectMapper.writeValueAsString(entity)).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		deleteMockEntity(entity);
	}
	
	@Test
	void testRestApiGetByPerson() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		ProductReview entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.get("/find-product-review-by-person/" + entity.getIndividual().getId())
				.content(objectMapper.writeValueAsString(entity)).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		deleteMockEntity(entity);
	}
	
	@Test
	void testRestApiGetByProductPerson() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		ProductReview entity = createMockEntity();
		entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.get("/find-product-review-by-product-person/" + entity.getProduct().getId() + "/" + entity.getIndividual().getId())
				.content(objectMapper.writeValueAsString(entity)).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		deleteMockEntity(entity);
	}

	private ProductReview createMockEntity() {

		Individual individual = RandomEntityGenerator.generateIndividual();
	    individualRepository.save(individual);
		
		LegalEntity legalEntity = RandomEntityGenerator.generateLegalEntity();
		legalEntityRepository.save(legalEntity);

		ProductBrand productBrand = RandomEntityGenerator.createProductBrand();
		productBrand.setLegalEntity(legalEntity);
		productBrandRepository.save(productBrand);

		ProductCategory productCategory = RandomEntityGenerator.createProductCategory();
		productCategory.setLegalEntity(legalEntity);
		productCategoryRepository.save(productCategory);

		Product product = RandomEntityGenerator.createProduct();
		product.setLegalEntity(legalEntity);
		product.setProductCategory(productCategory);
		product.setProductBrand(productBrand);
		productRepository.save(product);

		ProductReview entity = RandomEntityGenerator.createProductReview();
		entity.setIndividual(individual);
		entity.setLegalEntity(legalEntity);
		entity.setProduct(product);

		return entity;
	}

	private void deleteMockEntity(ProductReview entity) {

		entityRepository.deleteById(entity.getId());
		productRepository.deleteById(entity.getProduct().getId());
		productCategoryRepository.deleteById(entity.getProduct().getProductCategory().getId());
		productBrandRepository.deleteById(entity.getProduct().getProductBrand().getId());
		individualRepository.deleteById(entity.getIndividual().getId());
		legalEntityRepository.deleteById(entity.getLegalEntity().getId());

	}
}
