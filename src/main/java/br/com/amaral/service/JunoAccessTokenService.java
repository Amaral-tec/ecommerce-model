package br.com.amaral.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import br.com.amaral.model.BillingAccessToken;

@Service
public class JunoAccessTokenService {

	@PersistenceContext
	private EntityManager entityManager;

	public BillingAccessToken getActiveToken() {

		try {
			BillingAccessToken billingAccessToken = (BillingAccessToken) entityManager
					.createQuery("SELECT a FROM BillingAccessToken a ").setMaxResults(1).getSingleResult();

			return billingAccessToken;

		} catch (NoResultException e) {
			return null;
		}
	}

}
