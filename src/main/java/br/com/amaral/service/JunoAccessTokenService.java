package br.com.amaral.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import br.com.amaral.model.JunoAccessToken;

@Service
public class JunoAccessTokenService {

	@PersistenceContext
	private EntityManager entityManager;

	public JunoAccessToken getActiveToken() {

		try {
			JunoAccessToken accessTokenJuno = (JunoAccessToken) entityManager
					.createQuery("SELECT a FROM AccessTokenJuno a ").setMaxResults(1).getSingleResult();

			return accessTokenJuno;

		} catch (NoResultException e) {
			return null;
		}
	}

}
