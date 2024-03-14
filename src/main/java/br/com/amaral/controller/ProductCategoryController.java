package br.com.amaral.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.amaral.ExceptionProject;
import br.com.amaral.model.ProductCategory;
import br.com.amaral.repository.IProductCategoryRepository;

@RestController
public class ProductCategoryController {

	@Autowired
	private IProductCategoryRepository productCategoryRepository;
	
	@Autowired
    private LogController<ProductCategory> logController;

	@ResponseBody
	@PostMapping(value = "**/create-product-category")
	public ResponseEntity<ProductCategory> createProductCategory(@RequestBody @Valid ProductCategory entity)
			throws ExceptionProject {

		boolean result = productCategoryRepository.isProductCategoryRegistered(entity.getDescription());
		if (result) {
			throw new ExceptionProject(
					"Operation not carried out: Already registered with the name " + entity.getDescription());
		}

		productCategoryRepository.saveAndFlush(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/delete-product-category")
	public ResponseEntity<String> deleteProductCategory(@RequestBody ProductCategory entity) {

		if (!productCategoryRepository.findById(entity.getId()).isPresent()) {
			return new ResponseEntity<>("Operation not performed: Not included in the ID database: " + entity.getId(), HttpStatus.NOT_FOUND);
		}
		
		entity.setIsDeleted(true);
		productCategoryRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping(value = "**/delete-product-category-by-id/{id}")
	public ResponseEntity<String> deleteProductCategoryById(@PathVariable("id") Long id) throws ExceptionProject {
		
		ProductCategory entity = isExist(id);
		
		entity.setIsDeleted(true);
		productCategoryRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/get-product-category/{id}")
	public ResponseEntity<ProductCategory> getProductCategory(@PathVariable("id") Long id) throws ExceptionProject {

		ProductCategory entity = isExist(id);

		logController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/find-all-product-category")
	public ResponseEntity<List<ProductCategory>> findAllProductCategory() {

		List<ProductCategory> list = productCategoryRepository.findAll();

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/find-product-category-by-name/{description}")
	public ResponseEntity<List<ProductCategory>> findProductCategoryByName(@PathVariable("description") String description) {

		List<ProductCategory> list = productCategoryRepository.findCategoryProductByDescription(description.toUpperCase());

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	private ProductCategory isExist(Long id) throws ExceptionProject {

		ProductCategory entity = productCategoryRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database: " + id);
		}

		return entity;
	}
}
