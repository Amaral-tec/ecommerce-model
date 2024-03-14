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
import br.com.amaral.model.ProductReview;
import br.com.amaral.repository.IProductReviewRepository;

@RestController
public class ProductReviewController {

	@Autowired
	private IProductReviewRepository productReviewRepository;
	
	@Autowired
    private LogController<ProductReview> logController;

	@ResponseBody
	@PostMapping(value = "**/create-product-review")
	public ResponseEntity<ProductReview> createProductReview(@RequestBody @Valid ProductReview entity)
			throws ExceptionProject {

		if (entity.getLegalEntity() == null
				|| (entity.getLegalEntity() != null && entity.getLegalEntity().getId() <= 0)) {
			throw new ExceptionProject("The Legal Entity field must be informed.");
		}
		if (entity.getProduct() == null
				|| (entity.getProduct() != null && entity.getProduct().getId() <= 0)) {
			throw new ExceptionProject("The review must contain the associated product.");
		}
		if (entity.getIndividual() == null
				|| (entity.getIndividual() != null && entity.getIndividual().getId() <= 0)) {
			throw new ExceptionProject("The Individual field must be informed.");
		}

		productReviewRepository.saveAndFlush(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/delete-product-review")
	public ResponseEntity<String> deleteProductReview(@RequestBody ProductReview entity) {

		if (!productReviewRepository.findById(entity.getId()).isPresent()) {
			return new ResponseEntity<>("Operation not performed: Not included in the ID database: " + entity.getId(), HttpStatus.NOT_FOUND);
		}
		
		entity.setIsDeleted(true);
		productReviewRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping(value = "**/delete-product-review-by-id/{id}")
	public ResponseEntity<String> deleteProductReviewById(@PathVariable("id") Long id) throws ExceptionProject {
		
		ProductReview entity = isExist(id);
		
		entity.setIsDeleted(true);
		productReviewRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/find-product-review-by-product/{productId}")
	public ResponseEntity<List<ProductReview>> findProductReviewByProduct(@PathVariable("productId") Long productId) {

		List<ProductReview> list = productReviewRepository.findProductReviewByProduct(productId);

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/find-product-review-by-person/{individualId}")
	public ResponseEntity<List<ProductReview>> findProductReviewByPerson(@PathVariable("individualId") Long individualId) {

		List<ProductReview> list = productReviewRepository.findProductReviewByPerson(individualId);

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/find-product-review-by-product-person/{productId}/{individualId}")
	public ResponseEntity<List<ProductReview>> findProductReviewByProductPerson(@PathVariable("productId") Long productId,
			@PathVariable("individualId") Long individualId) {

		List<ProductReview> list = productReviewRepository.findProductReviewByProductPerson(productId,
				individualId);

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	} 
	
	private ProductReview isExist(Long id) throws ExceptionProject {

		ProductReview entity = productReviewRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database: " + id);
		}

		return entity;
	}

}
