package br.com.amaral.service;

import java.util.Calendar;

import org.springframework.stereotype.Service;

import br.com.amaral.model.Access;
import br.com.amaral.model.PaymentMethod;

@Service
public class RandomEntityGenerator {

	public Access createAccess() {

		Access access = new Access();
		access.setDescription("Test Name" + Calendar.getInstance().getTimeInMillis());

		return access;
	}
	
	public PaymentMethod createPaymentMethod() {

		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setDescription("Test Name" + Calendar.getInstance().getTimeInMillis());

		return paymentMethod;
	}
}
