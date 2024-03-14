package br.com.amaral.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import br.com.amaral.ApplicationContextLoad;
import br.com.amaral.repository.IUserRepository;

/*Create and resume JWT authentication*/
@Service
@Component
public class JWTTokenAuthenticationService {

	/* 11-day validity Token */
	private static final long EXPIRATION_TIME = 959990000;

	/* Password key to join with JWT */
	private static final String SECRET = "ss/-*-*lan565jmms-s/d-s*educ";

	private static final String TOKEN_PREFIX = "Bearer";

	private static final String HEADER_STRING = "Authorization";

	/* Generates the token and gives a response to the user with JWT */
	public void addAuthentication(HttpServletResponse response, String username) throws Exception {

		/* Token Assembly */
		String JWT = Jwts.builder()/* Calls the token generator */
				.setSubject(username) /* Add the user */
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact(); /* Expiration time */

		String token = TOKEN_PREFIX + " " + JWT;

		/*
		 * Gives the response to the screen and to the user, another API, browser,
		 * application, JavaScript, etc.
		 */
		response.addHeader(HEADER_STRING, token);

		clearCOrs(response);

		/* Used to view in Postman during testing */
		response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
	}

	/*
	 * Returns the user validated with a token or if it is not valid, returns null
	 */
	public Authentication getAuthetication(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String token = request.getHeader(HEADER_STRING);

		try {

			if (token != null) {

				String cleanToken = token.replace(TOKEN_PREFIX, "").trim();

				/* Validates the user token in the request and obtains the USER */
				String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(cleanToken).getBody().getSubject();

				if (user != null) {

					br.com.amaral.model.User localUser = ApplicationContextLoad.getApplicationContext().getBean(IUserRepository.class)
							.getUserByLogin(user);

					if (localUser != null) {
						return new UsernamePasswordAuthenticationToken(
								localUser.getLogin(), 
								localUser.getPassword(),
								localUser.getAuthorities());
					}
				}
			}

		} catch (SignatureException e) {
			response.getWriter().write("Invalid token.");

		} catch (ExpiredJwtException e) {
			response.getWriter().write("Token expired, log in again.");
		} finally {
			clearCOrs(response);
		}

		return null;
	}

	private void clearCOrs(HttpServletResponse response) {

		if (response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}
		if (response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", "*");
		}
		if (response.getHeader("Access-Control-Request-Headers") == null) {
			response.addHeader("Access-Control-Request-Headers", "*");
		}
		if (response.getHeader("Access-Control-Allow-Methods") == null) {
			response.addHeader("Access-Control-Allow-Methods", "*");
		}
	}

}
