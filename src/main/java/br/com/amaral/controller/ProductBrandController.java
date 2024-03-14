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
import br.com.amaral.model.ProductBrand;
import br.com.amaral.repository.IProductBrandRepository;

@RestController
public class ProductBrandController {

	@Autowired
	private IProductBrandRepository productBrandRepository;

	@Autowired
	private LogController<ProductBrand> logController;

	@ResponseBody
	@PostMapping(value = "**/create-product-brand")
	public ResponseEntity<ProductBrand> createProductBrand(@RequestBody @Valid ProductBrand entity)
			throws ExceptionProject {

		boolean result = productBrandRepository.isProductBrandRegistered(entity.getDescription());
		if (result) {
			throw new ExceptionProject(
					"Operation not carried out: Already registered with the name " + entity.getDescription());
		}

		productBrandRepository.saveAndFlush(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping(value = "**/delete-product-brand")
	public ResponseEntity<String> deleteProductBrand(@RequestBody ProductBrand entity) {

		if (!productBrandRepository.findById(entity.getId()).isPresent()) {
			return new ResponseEntity<>("Operation not performed: Not included in the ID database: " + entity.getId(),
					HttpStatus.NOT_FOUND);
		}

		entity.setIsDeleted(true);
		productBrandRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping(value = "**/delete-product-brand-by-id/{id}")
	public ResponseEntity<String> deleteProductBrandById(@PathVariable("id") Long id) throws ExceptionProject {

		ProductBrand entity = isExist(id);

		entity.setIsDeleted(true);
		productBrandRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/get-product-Brand/{id}")
	public ResponseEntity<ProductBrand> getProductBrand(@PathVariable("id") Long id) throws ExceptionProject {

		ProductBrand entity = isExist(id);

		logController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/find-all-product-brand")
	public ResponseEntity<List<ProductBrand>> findAllProductBrand() {

		List<ProductBrand> list = productBrandRepository.findAll();

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/find-product-brand-by-name/{description}")
	public ResponseEntity<List<ProductBrand>> findProductBrandByName(@PathVariable("description") String description) {

		List<ProductBrand> list = productBrandRepository.findProductBrandByName(description.toUpperCase());

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	private ProductBrand isExist(Long id) throws ExceptionProject {

		ProductBrand entity = productBrandRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database: " + id);
		}

		return entity;
	}

}
