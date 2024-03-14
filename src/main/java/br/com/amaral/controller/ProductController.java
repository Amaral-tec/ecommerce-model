package br.com.amaral.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
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
import br.com.amaral.model.Product;
import br.com.amaral.model.ProductImage;
import br.com.amaral.repository.IProductRepository;
import br.com.amaral.service.ProductService;


@RestController
public class ProductController {

	@Autowired
	private IProductRepository productRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
    private LogController<Product> logController;

	@ResponseBody
	@PostMapping(value = "**/create-product")
	public ResponseEntity<Product> createProduct(@RequestBody @Valid Product entity)
			throws ExceptionProject, MessagingException, IOException {

		if (productRepository.isProductRegistered(entity.getName())) {
            throw new ExceptionProject("Operation not carried out: Already registered with the name " + entity.getName());
        }

        List<ProductImage> images = entity.getImages();
        if (images == null || images.isEmpty() || images.size() > 6) {
            throw new ExceptionProject("The product must contain between 1 and 6 associated images.");
		}
		if (entity.getId() == null) {

			productService.saveProductImages(entity);
		}

		productRepository.save(entity);
		productService.sendLowStockAlert(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/delete-product")
	public ResponseEntity<String> deleteProduct(@RequestBody Product entity) {

		if (!productRepository.findById(entity.getId()).isPresent()) {
			return new ResponseEntity<>("Operation not performed: Not included in the ID database " + entity.getId(),
					HttpStatus.NOT_FOUND);
		}

		entity.setIsDeleted(true);
		productRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping(value = "**/delete-product-by-id/{id}")
	public ResponseEntity<String> deleteProductById(@PathVariable("id") Long id) throws ExceptionProject {

		Product entity = isExist(id);

		entity.setIsDeleted(true);
		productRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/get-product/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) throws ExceptionProject {

		Product entity = isExist(id);

		logController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/find-all-product")
	public ResponseEntity<List<Product>> findAllProduct() {

		List<Product> list = productRepository.findAll();

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/find-product-by-name/{name}")
	public ResponseEntity<List<Product>> findProductByName(@PathVariable("name") String name) {

		List<Product> list = productRepository.findProductByName(name.toUpperCase());

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	private Product isExist(Long id) throws ExceptionProject {

		Product entity = productRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database: " + id);
		}

		return entity;
	}
}
