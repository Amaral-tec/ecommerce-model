package br.com.amaral.service;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import br.com.amaral.enums.TypePerson;
import br.com.amaral.model.Individual;

public class RandomIndividualGenerator {

public static Individual generateIndividual() throws SQLException {
        
        String cpf = RandomCPFGenerator.generateNewCPF();        
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        Individual individual = new Individual();
        individual.setTypePerson(TypePerson.INDIVIDUAL);
        individual.setName("Test Name");
        individual.setEmail("email-test-" + Calendar.getInstance().getTimeInMillis() + "@test.com");    
        individual.setPhone("45999795800");
        individual.setCreatedAt(date);   
        individual.setCpf(cpf);
        individual.setDateBirth(date);

        return individual;       
    }
}
