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

import br.com.amaral.model.Product;
import br.com.amaral.model.ProductBrand;
import br.com.amaral.model.ProductCategory;
import br.com.amaral.model.ProductImage;
import br.com.amaral.model.LegalEntity;
import br.com.amaral.repository.IProductRepository;
import br.com.amaral.repository.ILegalEntityRepository;
import br.com.amaral.repository.IProductBrandRepository;
import br.com.amaral.repository.IProductCategoryRepository;
import br.com.amaral.repository.IProductImageRepository;
import br.com.amaral.service.RandomEntityGenerator;
import junit.framework.TestCase;

@Profile("test")
@SpringBootTest
class ProductTests extends TestCase {

	@Autowired
	private IProductRepository entityRepository;
	
	@Autowired
	private IProductBrandRepository productBrandRepository;
	
	@Autowired
	private IProductCategoryRepository productCategoryRepository;
	
	@Autowired
	private IProductImageRepository productImageRepository;
		
	@Autowired
	private ILegalEntityRepository legalEntityRepository;
	
	@Autowired
	private WebApplicationContext wac;

	@Test
	void testRestApiSave() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
	    MockMvc mockMvc = builder.build();
	    
	    LegalEntity legalEntity = RandomEntityGenerator.generateLegalEntity();
	    legalEntityRepository.save(legalEntity);
	    
	    ProductBrand productBrand = RandomEntityGenerator.createProductBrand();
	    productBrand.setLegalEntity(legalEntity);
	    productBrandRepository.save(productBrand);
	    
	    ProductCategory productCategory = RandomEntityGenerator.createProductCategory();
	    productCategory.setLegalEntity(legalEntity);
	    productCategoryRepository.save(productCategory);
	    
	    List<ProductImage> productImages = new ArrayList<>();
	    productImages.add(RandomEntityGenerator.createProductImage());
	    productImages.add(RandomEntityGenerator.createProductImage());

	    Product entity = RandomEntityGenerator.createProduct();
	    entity.setLegalEntity(legalEntity);
	    entity.setProductCategory(productCategory);
	    entity.setProductBrand(productBrand);
	    entity.setImages(productImages);
	    
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    ResultActions returnApi = mockMvc
	    						 .perform(MockMvcRequestBuilders.post("/create-product")
	    						 .content(objectMapper.writeValueAsString(entity))
	    						 .accept(MediaType.APPLICATION_JSON)
	    						 .contentType(MediaType.APPLICATION_JSON));
	     
	    System.out.println("API Response: " + returnApi.andReturn().getResponse().getContentAsString());
	     
	    Product returnEntity = objectMapper.readValue(returnApi.andReturn().getResponse().getContentAsString(),
	    		Product.class);
	    
	    assertEquals(entity.getDescription(), returnEntity.getDescription());
	    

	    for (ProductImage productImage : returnEntity.getImages()) {
	    	productImageRepository.deleteById(productImage.getId());
	    }
	    
	    entityRepository.deleteById(returnEntity.getId());
	    productCategoryRepository.deleteById(productCategory.getId());
	    productBrandRepository.deleteById(productBrand.getId());   
	    legalEntityRepository.deleteById(legalEntity.getId());
	}

	@Test
	void testRestApiDelete() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		LegalEntity legalEntity = RandomEntityGenerator.generateLegalEntity();
	    legalEntityRepository.save(legalEntity);
	    
	    ProductBrand productBrand = RandomEntityGenerator.createProductBrand();
	    productBrand.setLegalEntity(legalEntity);
	    productBrandRepository.save(productBrand);
	    
	    ProductCategory productCategory = RandomEntityGenerator.createProductCategory();
	    productCategory.setLegalEntity(legalEntity);
	    productCategoryRepository.save(productCategory);

	    Product entity = RandomEntityGenerator.createProduct();
	    entity.setLegalEntity(legalEntity);
	    entity.setProductCategory(productCategory);
	    entity.setProductBrand(productBrand);
		entity = entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc
				.perform(MockMvcRequestBuilders.post("/delete-product").content(objectMapper.writeValueAsString(entity))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		System.out.println("API Response: " + returnApi.andReturn().getResponse().getContentAsString());
		System.out.println("API Status: " + returnApi.andReturn().getResponse().getStatus());

		assertEquals("OK: Deletion completed successfully.", returnApi.andReturn().getResponse().getContentAsString());
		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		entityRepository.deleteById(entity.getId());	   
	    productCategoryRepository.deleteById(productCategory.getId());
	    productBrandRepository.deleteById(productBrand.getId());   
	    legalEntityRepository.deleteById(legalEntity.getId());
	}
	
	@Test
	void testRestApiDeleteById() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		LegalEntity legalEntity = RandomEntityGenerator.generateLegalEntity();
	    legalEntityRepository.save(legalEntity);
	    
	    ProductBrand productBrand = RandomEntityGenerator.createProductBrand();
	    productBrand.setLegalEntity(legalEntity);
	    productBrandRepository.save(productBrand);
	    
	    ProductCategory productCategory = RandomEntityGenerator.createProductCategory();
	    productCategory.setLegalEntity(legalEntity);
	    productCategoryRepository.save(productCategory);

	    Product entity = RandomEntityGenerator.createProduct();
	    entity.setLegalEntity(legalEntity);
	    entity.setProductCategory(productCategory);
	    entity.setProductBrand(productBrand);
		entity = entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders
				.delete("/delete-product-by-id/" + entity.getId()).content(objectMapper.writeValueAsString(entity))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		System.out.println("API Response: " + returnApi.andReturn().getResponse().getContentAsString());
		System.out.println("API Status: " + returnApi.andReturn().getResponse().getStatus());

		assertEquals("OK: Deletion completed successfully.", returnApi.andReturn().getResponse().getContentAsString());
		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		entityRepository.deleteById(entity.getId());	   
	    productCategoryRepository.deleteById(productCategory.getId());
	    productBrandRepository.deleteById(productBrand.getId());   
	    legalEntityRepository.deleteById(legalEntity.getId());
	}
	
	@Test
	void testRestApiGetById() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		LegalEntity legalEntity = RandomEntityGenerator.generateLegalEntity();
	    legalEntityRepository.save(legalEntity);
	    
	    ProductBrand productBrand = RandomEntityGenerator.createProductBrand();
	    productBrand.setLegalEntity(legalEntity);
	    productBrandRepository.save(productBrand);
	    
	    ProductCategory productCategory = RandomEntityGenerator.createProductCategory();
	    productCategory.setLegalEntity(legalEntity);
	    productCategoryRepository.save(productCategory);

	    Product entity = RandomEntityGenerator.createProduct();
	    entity.setLegalEntity(legalEntity);
	    entity.setProductCategory(productCategory);
	    entity.setProductBrand(productBrand);
		entity = entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.get("/get-product/" + entity.getId())
				.content(objectMapper.writeValueAsString(entity)).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		Product returnEntity = objectMapper.readValue(returnApi.andReturn().getResponse().getContentAsString(),
				Product.class);

		assertEquals(entity.getId(), returnEntity.getId());

		entityRepository.deleteById(entity.getId());	   
	    productCategoryRepository.deleteById(productCategory.getId());
	    productBrandRepository.deleteById(productBrand.getId());   
	    legalEntityRepository.deleteById(legalEntity.getId());
	}
	
	@Test
	void testRestApiFindAll() throws JsonProcessingException, Exception {

	    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
	    MockMvc mockMvc = builder.build();

	    LegalEntity legalEntity = RandomEntityGenerator.generateLegalEntity();
	    legalEntityRepository.save(legalEntity);
	    
	    ProductBrand productBrand = RandomEntityGenerator.createProductBrand();
	    productBrand.setLegalEntity(legalEntity);
	    productBrandRepository.save(productBrand);
	    
	    ProductCategory productCategory = RandomEntityGenerator.createProductCategory();
	    productCategory.setLegalEntity(legalEntity);
	    productCategoryRepository.save(productCategory);

	    Product entity = RandomEntityGenerator.createProduct();
	    entity.setLegalEntity(legalEntity);
	    entity.setProductCategory(productCategory);
	    entity.setProductBrand(productBrand);
		entity = entityRepository.save(entity);

	    ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.get("/find-all-product")
	            .accept(MediaType.APPLICATION_JSON)
	            .contentType(MediaType.APPLICATION_JSON));

	    assertEquals(200, returnApi.andReturn().getResponse().getStatus());

	    entityRepository.deleteById(entity.getId());	   
	    productCategoryRepository.deleteById(productCategory.getId());
	    productBrandRepository.deleteById(productBrand.getId());   
	    legalEntityRepository.deleteById(legalEntity.getId());
	}
	
	@Test
	void testRestApiGetByDescription() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		LegalEntity legalEntity = RandomEntityGenerator.generateLegalEntity();
	    legalEntityRepository.save(legalEntity);
	    
	    ProductBrand productBrand = RandomEntityGenerator.createProductBrand();
	    productBrand.setLegalEntity(legalEntity);
	    productBrandRepository.save(productBrand);
	    
	    ProductCategory productCategory = RandomEntityGenerator.createProductCategory();
	    productCategory.setLegalEntity(legalEntity);
	    productCategoryRepository.save(productCategory);

	    Product entity = RandomEntityGenerator.createProduct();
	    entity.setLegalEntity(legalEntity);
	    entity.setProductCategory(productCategory);
	    entity.setProductBrand(productBrand);
		entity = entityRepository.save(entity);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders
				.get("/find-product-by-name/" + entity.getDescription()).content(objectMapper.writeValueAsString(entity))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, returnApi.andReturn().getResponse().getStatus());

		entityRepository.deleteById(entity.getId());	   
	    productCategoryRepository.deleteById(productCategory.getId());
	    productBrandRepository.deleteById(productBrand.getId());   
	    legalEntityRepository.deleteById(legalEntity.getId());
	}

}
