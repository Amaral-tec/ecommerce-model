package br.com.amaral.service;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import br.com.amaral.enums.TypePerson;
import br.com.amaral.model.LegalEntity;

public class RandomLegalPersonGenerator {

public static LegalEntity generateLegalEntity() throws SQLException {
        
        String cnpj = RandomCNPJGenerator.generateNewCNPJ();    
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setTypePerson(TypePerson.LEGAL);
        legalEntity.setName("Test Name");
        legalEntity.setEmail("email-test-" + Calendar.getInstance().getTimeInMillis() + "@test.com");    
        legalEntity.setPhone("45999795800");
        legalEntity.setCreatedAt(date);  
        legalEntity.setCnpj(cnpj);
        legalEntity.setTradeName("Test Trade Name");
        legalEntity.setStateTaxID("200200");
        legalEntity.setDistrictTaxID("1000100");

        return legalEntity;    
    }
}
