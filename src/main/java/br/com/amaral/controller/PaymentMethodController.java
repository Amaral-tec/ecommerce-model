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
import br.com.amaral.model.PaymentMethod;
import br.com.amaral.repository.IPaymentMethodRepository;

@RestController
public class PaymentMethodController {

	@Autowired
	private IPaymentMethodRepository paymentMethodRepository;
	
	@Autowired
    private LogController<PaymentMethod> logController;

	@ResponseBody
	@PostMapping(value = "**/create-payment-method")
	public ResponseEntity<PaymentMethod> createPaymentMethod(@RequestBody @Valid PaymentMethod entity)
			throws ExceptionProject {

		boolean result = paymentMethodRepository.isPaymentMethodRegistered(entity.getDescription());
		if (result) {
			throw new ExceptionProject(
					"Operation not carried out: Already registered with the name " + entity.getDescription());
		}

		paymentMethodRepository.saveAndFlush(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/delete-payment-method")
	public ResponseEntity<String> deletePaymentMethod(@RequestBody PaymentMethod entity) {

		if (!paymentMethodRepository.findById(entity.getId()).isPresent()) {
			return new ResponseEntity<>("Operation not performed: Not included in the ID database: " + entity.getId(), HttpStatus.NOT_FOUND);
		}
		
		entity.setIsDeleted(true);
		paymentMethodRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping(value = "**/delete-payment-method-by-id/{id}")
	public ResponseEntity<String> deletePaymentMethodById(@PathVariable("id") Long id) throws ExceptionProject {
		
		PaymentMethod entity = isExist(id);
		
		entity.setIsDeleted(true);
		paymentMethodRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/get-payment-method/{id}")
	public ResponseEntity<PaymentMethod> getPaymentMethod(@PathVariable("id") Long id) throws ExceptionProject {

		PaymentMethod entity = isExist(id);

		logController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/find-all-payment-method")
	public ResponseEntity<List<PaymentMethod>> findAllPaymentMethodByName() {

		List<PaymentMethod> list = paymentMethodRepository.findAll();

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/find-payment-method-by-name/{description}")
	public ResponseEntity<List<PaymentMethod>> findPaymentMethodByName(@PathVariable("description") String description) {

		List<PaymentMethod> list = paymentMethodRepository.findPaymentMethodByName(description.toUpperCase());

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	private PaymentMethod isExist(Long id) throws ExceptionProject {

		PaymentMethod entity = paymentMethodRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database: " + id);
		}

		return entity;
	}
}
