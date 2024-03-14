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
import br.com.amaral.model.Individual;
import br.com.amaral.model.LegalEntity;
import br.com.amaral.repository.IIndividualRepository;
import br.com.amaral.repository.ILegalEntityRepository;
import br.com.amaral.service.PersonService;
import br.com.amaral.util.ValidateCNPJ;
import br.com.amaral.util.ValidateCPF;

@RestController
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@Autowired
    private LogController<LegalEntity> legalEntityLogController;
	
	@Autowired
    private LogController<Individual> individualLogController;
	
	@Autowired
	private ILegalEntityRepository legalEntityRepository;
	
	@Autowired
	private IIndividualRepository individualRepository;
	
	@ResponseBody
	@PostMapping(value = "**/create-legal-entity")
	public ResponseEntity<LegalEntity> createLegalEntity(@RequestBody @Valid LegalEntity entity)
			throws ExceptionProject {

		boolean result = legalEntityRepository.isCnpjRegistered(entity.getCnpj());
		if (result) {
			throw new ExceptionProject(
					"Operation not carried out: Already registered with the number " + entity.getCnpj());
		}

		if (!ValidateCNPJ.isCNPJ(entity.getCnpj())) {
			throw new ExceptionProject("CNPJ: " + entity.getCnpj() + " does not correspond to a valid CNPJ.");
		}
	
		entity = personService.saveLegalEntity(entity);
		legalEntityLogController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/create-individual")
	public ResponseEntity<Individual> createIndividual(@RequestBody @Valid Individual entity)
			throws ExceptionProject {

		boolean result = individualRepository.isCpfRegistered(entity.getCpf());
		if (result) {
			throw new ExceptionProject(
					"Operation not carried out: Already registered with the number " + entity.getCpf());
		}

		if (!ValidateCPF.isCPF(entity.getCpf())) {
			throw new ExceptionProject("CPF: " + entity.getCpf() + " does not correspond to a valid CPF.");
		}
	
		entity = personService.saveIndividual(entity);
		individualLogController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping(value = "**/delete-legal-entity")
	public ResponseEntity<String> deleteLegalEntity(@RequestBody LegalEntity entity) {

		if (!legalEntityRepository.findById(entity.getId()).isPresent()) {
			return new ResponseEntity<>("Operation not performed: Not included in the ID database " + entity.getId(), HttpStatus.NOT_FOUND);
		}
		
		entity.setIsDeleted(true);
		legalEntityRepository.save(entity);
		legalEntityLogController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/delete-individual")
	public ResponseEntity<String> deleteIndividual(@RequestBody Individual entity) {

		if (!individualRepository.findById(entity.getId()).isPresent()) {
			return new ResponseEntity<>("Operation not performed: Not included in the ID database " + entity.getId(), HttpStatus.NOT_FOUND);
		}
		
		entity.setIsDeleted(true);
		individualRepository.save(entity);
		individualLogController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping(value = "**/delete-legal-entity-by-id/{id}")
	public ResponseEntity<String> deleteLegalEntityById(@PathVariable("id") Long id) throws ExceptionProject {
		
		LegalEntity entity = DoesLegalEntityExist(id);
		
		entity.setIsDeleted(true);
		legalEntityRepository.save(entity);
		legalEntityLogController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/delete-individual-by-id/{id}")
	public ResponseEntity<String> deleteIndividualById(@PathVariable("id") Long id) throws ExceptionProject {
		
		Individual entity = DoesIndividualExist(id);
		
		entity.setIsDeleted(true);
		individualRepository.save(entity);
		individualLogController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/get-legal-entity/{id}")
	public ResponseEntity<LegalEntity> getLegalEntity(@PathVariable("id") Long id) throws ExceptionProject {

		LegalEntity entity = DoesLegalEntityExist(id);
		
		legalEntityLogController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/get-individual/{id}")
	public ResponseEntity<Individual> getIndividual(@PathVariable("id") Long id) throws ExceptionProject {

		Individual entity = DoesIndividualExist(id);
		
		individualLogController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/get-legal-entity-by-cnpj/{cnpj}")
	public ResponseEntity<LegalEntity> getLegalEntityByCnpj(@PathVariable("cnpj") String cnpj) {

		LegalEntity entity = legalEntityRepository.getLegalEntityByCnpj(cnpj);
		
		legalEntityLogController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/get-individual-by-cpf/{cpf}")
	public ResponseEntity<Individual> getIndividualByCpf(@PathVariable("cpf") String cpf) {

		Individual entity = individualRepository.getIndividualByCpf(cpf);
		
		individualLogController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/find-all-legal-entity")
	public ResponseEntity<List<LegalEntity>> findAllLegalEntity() {

		List<LegalEntity> list = legalEntityRepository.findAll();

		legalEntityLogController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/find-all-individual")
	public ResponseEntity<List<Individual>> findAllIndividual() {

		List<Individual> list = individualRepository.findAll();

		individualLogController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/find-legal-entity-by-name/{name}")
	public ResponseEntity<List<LegalEntity>> findLegalEntityByName(@PathVariable("name") String name) {

		List<LegalEntity> list = legalEntityRepository.findLegalEntityByName(name.toUpperCase());

		legalEntityLogController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/find-individual-by-name/{name}")
	public ResponseEntity<List<Individual>> findIndividualByName(@PathVariable("name") String name) {

		List<Individual> list = individualRepository.findIndividualByName(name.toUpperCase());

		individualLogController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	private LegalEntity DoesLegalEntityExist(Long id) throws ExceptionProject {

		LegalEntity entity = legalEntityRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database: " + id);
		}

		return entity;
	}
	
	private Individual DoesIndividualExist(Long id) throws ExceptionProject {

		Individual entity = individualRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database: " + id);
		}

		return entity;
	}
}
