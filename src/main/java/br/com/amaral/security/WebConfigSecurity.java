package br.com.amaral.security;

import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.amaral.service.ImplementationUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter implements HttpSessionListener {
	
	@Autowired
	private ImplementationUserDetailsService implementationUserDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.disable().authorizeRequests().antMatchers("/").permitAll()
		.antMatchers("/index").permitAll()
		/*Avoid blocking COrs in the browser*/
		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		
		/*Redirects or returns to index when logged out of the system*/
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
		
		/*Maps system logout*/
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		
		/*Filter JWT login requests*/
		.and().addFilterAfter(new JWTLoginFilter("/login", authenticationManager()),
				UsernamePasswordAuthenticationFilter.class)
		
		.addFilterBefore(new JWTApiAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);	
	}
	
	/*Will query the user in the database with Spring Security*/
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(implementationUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		
	}

	/*Bypasses some authentication-free URLs*/
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.GET, "/find-all-access")
				.antMatchers(HttpMethod.POST, "/create-access");
	}

}
