package br.com.amaral.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.amaral.ExceptionProject;
import br.com.amaral.model.User;
import br.com.amaral.repository.IUserRepository;

@RestController
public class UserController {
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
    private LogController<User> logController;
	
	@ResponseBody
	@PostMapping(value = "**/create-user")
	public ResponseEntity<User> createUser(@RequestBody @Valid User entity) throws ExceptionProject {

		boolean result = userRepository.isUserRegistered(entity.getLogin());
		if (result) {
			throw new ExceptionProject(
					"Operation not carried out: Already registered with the login " + entity.getLogin());
		}
	
		String encryptedPassword = new BCryptPasswordEncoder().encode(entity.getPassword());
		entity.setPassword(encryptedPassword);
		entity.setPasswordCreatedAt(new Date());
		userRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/delete-user")
	public ResponseEntity<String> deleteUser(@RequestBody User entity) {

		if (!userRepository.findById(entity.getId()).isPresent()) {
			return new ResponseEntity<>("Operation not performed: Not included in the ID database " + entity.getId(), HttpStatus.NOT_FOUND);
		}
		
		entity.setIsDeleted(true);
		userRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping(value = "**/delete-user-by-id/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) throws ExceptionProject {
		
		User entity = isExist(id);
		
		entity.setIsDeleted(true);
		userRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/get-user/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") Long id) throws ExceptionProject {

		User entity = isExist(id);

		logController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/get-user-by-login/{login}")
	public ResponseEntity<User> getUserByLogin(@PathVariable("login") String login) {

		User entity = userRepository.getUserByLogin(login);

		logController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/find-all-user")
	public ResponseEntity<List<User>> findAllUser() {

		List<User> list = userRepository.findAll();

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	private User isExist(Long id) throws ExceptionProject {

		User entity = userRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database: " + id);
		}

		return entity;
	}
}
