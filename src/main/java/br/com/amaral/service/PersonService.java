package br.com.amaral.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import br.com.amaral.ExceptionProject;
import br.com.amaral.controller.UserController;
import br.com.amaral.model.Individual;
import br.com.amaral.model.LegalEntity;
import br.com.amaral.model.User;
import br.com.amaral.repository.IIndividualRepository;
import br.com.amaral.repository.ILegalEntityRepository;
import br.com.amaral.repository.IUserRepository;

@Service
public class PersonService {

	@Autowired
	private ILegalEntityRepository legalEntityRepository;
	
	@Autowired
	private IIndividualRepository individualRepository;
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ServiceSendEmail serviceSendEmail;

	public LegalEntity saveLegalEntity(LegalEntity entity) {
		
		for (int i = 0; i < entity.getAddresses().size(); i++) {
			entity.getAddresses().get(i).setPerson(entity);
		}

		legalEntityRepository.save(entity);
		
		return entity;
	}

	public Individual saveIndividual(Individual entity) throws ExceptionProject {
		
		for (int i = 0; i < entity.getAddresses().size(); i++) {
			entity.getAddresses().get(i).setPerson(entity);
		}

		individualRepository.save(entity);
		createUserFirstAccess(entity);
		
		return entity;
	}
	
	private void createUserFirstAccess (Individual entity) throws ExceptionProject {
		
		User user = userRepository.findUserByPerson(entity.getId(), entity.getEmail());
		
		if (user == null) {
			String constraint = userRepository.getConstraintAccess();
			if (constraint != null) {
				jdbcTemplate.execute("BEGIN; ALTER TABLE users_accesses DROP CONSTRAINT " + constraint + "; commit;");
			}

			user = new User();
			user.setPasswordCreatedAt(Calendar.getInstance().getTime());
			user.setIndividual(entity);
			user.setLogin(entity.getEmail());

			String passwordRandomFirstAccess = "" + Calendar.getInstance().getTimeInMillis();
			user.setPassword(passwordRandomFirstAccess);

			userController.createUser(user);
			userRepository.insertUserAccess(user.getId(), "ROLE_USER");
			sendAccessData(entity, passwordRandomFirstAccess);

		}
	}
	
	private void sendAccessData (Individual entity, String password) {
		
		String URL = "www.model.com";
		StringBuilder message = new StringBuilder();

		message.append("<html><body>");
	    message.append("<p>Hello, ").append(entity.getName()).append("!</p>");
	    message.append("<p>Welcome to our online store. You now have exclusive access to our services.</p>");
	    message.append("<p>Your login details:</p>");
	    message.append("<ul>");
	    message.append("<li><strong>Email:</strong> ").append(entity.getEmail()).append("</li>");
	    message.append("<li><strong>Password:</strong> ").append(password).append("</li>");
	    message.append("</ul>");
	    message.append("<p>Visit our website at " + URL + " to start exploring.</p>");
	    message.append("<p>Thank you for choosing our services!</p>");
	    message.append("</body></html>");


		try {
			serviceSendEmail.sendEmailHtml("Generated Access for Online Store", message.toString(),
					entity.getEmail());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
