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
import br.com.amaral.model.ProductImage;
import br.com.amaral.model.dto.ProductImageDTO;
import br.com.amaral.repository.IProductImageRepository;

@RestController
public class ProductImageController {

	@Autowired
	private IProductImageRepository productImageRepository;
	
	@Autowired
    private LogController<ProductImage> logController;

	@ResponseBody
	@PostMapping(value = "**/create-product-image")
	public ResponseEntity<ProductImageDTO> createProductImage(@RequestBody @Valid ProductImage entity) {

		entity = productImageRepository.saveAndFlush(entity);

		ProductImageDTO productImageDTO = new ProductImageDTO();
		productImageDTO.setId(entity.getId());
		productImageDTO.setOriginalImage(entity.getOriginalImage());
		productImageDTO.setThumbnailImage(entity.getThumbnailImage());
		productImageDTO.setProduct(entity.getProduct().getId());
		productImageDTO.setLegalEntity(entity.getLegalEntity().getId());

		logController.logEntity(entity);
		return new ResponseEntity<>(productImageDTO, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping(value = "**/delete-product-image")
	public ResponseEntity<String> deleteProductImage(@RequestBody ProductImage entity) {

		if (!productImageRepository.existsById(entity.getId())) {
			return new ResponseEntity<>(
					"Operation not performed: Not included in the ID database: " + entity.getId(),
					HttpStatus.NOT_FOUND);
		}

		entity.setIsDeleted(true);
		productImageRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping(value = "**/delete-product-image-by-id/{id}")
	public ResponseEntity<String> deleteProductImageById(@PathVariable("id") Long id) throws ExceptionProject {

		ProductImage entity = isExist(id);
		
		entity.setIsDeleted(true);
		productImageRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/delete-product-image-package/{productId}")
	public ResponseEntity<String> deleteProductImagePackage(@PathVariable("productId") Long productId) {

		productImageRepository.deleteProductImage(productId);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/get-product-image/{id}")
	public ResponseEntity<ProductImage> getProductImage(@PathVariable("id") Long id) throws ExceptionProject {

		ProductImage entity = isExist(id);

		logController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/find-all-product-image")
	public ResponseEntity<List<ProductImage>> findAllProductImage() {

		List<ProductImage> list = productImageRepository.findAll();

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/find-product-image-by-product/{productId}")
	public ResponseEntity<List<ProductImage>> findProductImageByProduct(@PathVariable("productId") Long productId) {

		List<ProductImage> list = productImageRepository.findProductImageByProduct(productId);

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	private ProductImage isExist(Long id) throws ExceptionProject {

		ProductImage entity = productImageRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database: " + id);
		}

		return entity;
	}

}
