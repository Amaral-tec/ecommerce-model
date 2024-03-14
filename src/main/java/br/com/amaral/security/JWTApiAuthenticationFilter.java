package br.com.amaral.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/*Filter where all requests will be captured to authenticate*/
public class JWTApiAuthenticationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {

			/* Establishes user authentication */
			Authentication authentication = new JWTTokenAuthenticationService()
					.getAuthetication((HttpServletRequest) request, (HttpServletResponse) response);

			/* Install the authentication process for Spring Security */
			SecurityContextHolder.getContext().setAuthentication(authentication);

			/* Call or block the API */
			chain.doFilter(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter()
					.write("A system error has occurred, contact support: \n" + e.getMessage());
		}
	}
}
