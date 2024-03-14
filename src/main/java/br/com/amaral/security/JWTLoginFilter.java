package br.com.amaral.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	
	/*Configuring the authentication manager*/
	public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
	
		/*Requires authenticating the URL*/
		super(new AntPathRequestMatcher(url));
		
		/*Authentication Manager*/
		setAuthenticationManager(authenticationManager);
	}

	/*Returns the user when processing authentication*/
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		/*Get the user*/
		br.com.amaral.model.User user = new ObjectMapper().readValue(request.getInputStream(), br.com.amaral.model.User.class);
		
		/*Returns the user with login and password*/
		return getAuthenticationManager().
				authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		try {
			new JWTTokenAuthenticationService().addAuthentication(response, authResult.getName());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		if (failed instanceof BadCredentialsException) {
			response.getWriter().write("Username or password not found");
		}else {
			response.getWriter().write("Failed to log in: " + failed.getMessage());
		}
		
	}
}
