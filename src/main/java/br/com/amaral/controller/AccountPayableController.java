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
import br.com.amaral.model.AccountPayable;
import br.com.amaral.repository.IAccountPayableRepository;

@RestController
public class AccountPayableController {

	@Autowired
	private IAccountPayableRepository accountPayableRepository;
	
	@Autowired
    private LogController<AccountPayable> logController;

	@ResponseBody
	@PostMapping(value = "**/create-account-payable")
	public ResponseEntity<AccountPayable> createAccountPayable(@RequestBody @Valid AccountPayable entity) {

		accountPayableRepository.saveAndFlush(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/delete-account-payable")
	public ResponseEntity<String> deleteAccountPayable(@RequestBody AccountPayable entity) {

		if (!accountPayableRepository.findById(entity.getId()).isPresent()) {
			return new ResponseEntity<>("Operation not performed: Not included in the ID database: " + entity.getId(), HttpStatus.NOT_FOUND);
		}
		
		entity.setIsDeleted(true);
		accountPayableRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping(value = "**/delete-account-payable-by-id/{id}")
	public ResponseEntity<String> deleteAccountPayableById(@PathVariable("id") Long id) throws ExceptionProject {
		
		AccountPayable entity = isExist(id);
		
		entity.setIsDeleted(true);
		accountPayableRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/get-account-payable/{id}")
	public ResponseEntity<AccountPayable> getAccountPayable(@PathVariable("id") Long id) throws ExceptionProject {

		AccountPayable entity = isExist(id);

		logController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/find-all-account-payable")
	public ResponseEntity<List<AccountPayable>> findAllAccountPayable() {

		List<AccountPayable> list = accountPayableRepository.findAll();

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/find-account-payable-by-name/{description}")
	public ResponseEntity<List<AccountPayable>> findAccountPayableByName(@PathVariable("description") String description) {

		List<AccountPayable> list = accountPayableRepository.findAccountPayableByDescription(description.toUpperCase());

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/find-account-payable-by-person/{personId}")
	public ResponseEntity<List<AccountPayable>> findAccountPayableByPerson(@PathVariable("personId") Long personId) {

		List<AccountPayable> list = accountPayableRepository.findAccountPayableByPerson(personId);

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/find-account-payable-by-supplier/{supplierId}")
	public ResponseEntity<List<AccountPayable>> findAccountPayableBySupplier(@PathVariable("supplierId") Long supplierId) {

		List<AccountPayable> list = accountPayableRepository.findAccountPayableBySupplier(supplierId);

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/find-account-payable-by-legal-entity/{legalEntityId}")
	public ResponseEntity<List<AccountPayable>> findAccountPayableByLegalEntity(@PathVariable("legalEntityId") Long legalEntityId) {

		List<AccountPayable> list = accountPayableRepository.findAccountPayableByLegalEntity(legalEntityId);

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	private AccountPayable isExist(Long id) throws ExceptionProject {

		AccountPayable entity = accountPayableRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database: " + id);
		}

		return entity;
	}
	
}
