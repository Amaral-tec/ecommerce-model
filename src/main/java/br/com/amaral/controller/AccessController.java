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
import br.com.amaral.model.Access;
import br.com.amaral.repository.IAccessRepository;

@RestController
public class AccessController {

	@Autowired
	private IAccessRepository accessRepository;
	
	@Autowired
    private LogController<Access> logController;
	
	@ResponseBody
	@PostMapping(value = "**/create-access")
	public ResponseEntity<Access> createAccess(@RequestBody @Valid Access entity) throws ExceptionProject {

		List<Access> list = accessRepository.findAccessByName(entity.getDescription().toUpperCase());
		if (!list.isEmpty()) {
			throw new ExceptionProject(
					"Operation not carried out: Already registered with the name: " + entity.getDescription());
		}
		
		accessRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/delete-access")
	public ResponseEntity<String> deleteAccess(@RequestBody Access entity) {

		if (!accessRepository.findById(entity.getId()).isPresent()) {
			return new ResponseEntity<>("Operation not performed: Not included in the ID database " + entity.getId(), HttpStatus.NOT_FOUND);
		}
		
		entity.setIsDeleted(true);
		accessRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping(value = "**/delete-access-by-id/{id}")
	public ResponseEntity<String> deleteAccessById(@PathVariable("id") Long id) throws ExceptionProject {
		
		Access entity = isExist(id);
		
		entity.setIsDeleted(true);
		accessRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/get-access/{id}")
	public ResponseEntity<Access> getAccess(@PathVariable("id") Long id) throws ExceptionProject {

		Access entity = isExist(id);
		
		logController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/find-all-access")
	public ResponseEntity<List<Access>> findAllAccess() {

		List<Access> list = accessRepository.findAll();

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/find-access-by-name/{description}")
	public ResponseEntity<List<Access>> findAccessByName(@PathVariable("description") String description) {

		List<Access> list = accessRepository.findAccessByName(description.toUpperCase());

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	private Access isExist(Long id) throws ExceptionProject {

		Access entity = accessRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database: " + id);
		}

		return entity;
	}
}
