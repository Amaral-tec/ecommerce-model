package br.com.amaral.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.amaral.enums.TypePerson;
import br.com.amaral.model.Access;
import br.com.amaral.model.DiscountCoupon;
import br.com.amaral.model.Individual;
import br.com.amaral.model.LegalEntity;
import br.com.amaral.model.PaymentMethod;

@Service
public class RandomEntityGenerator {

	private static final Calendar calendar = Calendar.getInstance();
	private static final Date date = calendar.getTime();
	private static final BigDecimal value = new BigDecimal("100.0");

	public static Access createAccess() {

		Access access = new Access();
		access.setDescription("Test Name" + Calendar.getInstance().getTimeInMillis());

		return access;
	}

	public static DiscountCoupon createDiscountCoupon() {

		DiscountCoupon discountCoupon = new DiscountCoupon();
		discountCoupon.setCode("Test Name" + Calendar.getInstance().getTimeInMillis());
		discountCoupon.setDiscountAmount(value);
		discountCoupon.setDueDate(date);
		discountCoupon.setPercentage(false);
		discountCoupon.setExpired(false);

		return discountCoupon;
	}

	public static Individual generateIndividual() {

		String cpf = RandomCPFGenerator.generateNewCPF();

		Individual individual = new Individual();
		individual.setTypePerson(TypePerson.INDIVIDUAL);
		individual.setName("Test Name" + Calendar.getInstance().getTimeInMillis());
		individual.setEmail("email-test-" + Calendar.getInstance().getTimeInMillis() + "@test.com");
		individual.setPhone("45999795800");
		individual.setCreatedAt(date);
		individual.setCpf(cpf);
		individual.setDateBirth(date);

		return individual;
	}

	public static LegalEntity generateLegalEntity() throws SQLException {

		String cnpj = RandomCNPJGenerator.generateNewCNPJ();

		LegalEntity legalEntity = new LegalEntity();
		legalEntity.setTypePerson(TypePerson.LEGAL);
		legalEntity.setName("Test Name" + Calendar.getInstance().getTimeInMillis());
		legalEntity.setEmail("email-test-" + Calendar.getInstance().getTimeInMillis() + "@test.com");
		legalEntity.setPhone("45999795800");
		legalEntity.setCreatedAt(date);
		legalEntity.setCnpj(cnpj);
		legalEntity.setTradeName("Test Name" + Calendar.getInstance().getTimeInMillis());
		legalEntity.setStateTaxID("200200");
		legalEntity.setDistrictTaxID("1000100");

		return legalEntity;
	}

	public static PaymentMethod createPaymentMethod() {

		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setDescription("Test Name" + Calendar.getInstance().getTimeInMillis());

		return paymentMethod;
	}
}
