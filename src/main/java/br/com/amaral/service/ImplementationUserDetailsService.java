package br.com.amaral.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.amaral.repository.IUserRepository;

@Service
public class ImplementationUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		br.com.amaral.model.User localUser = userRepository.getUserByLogin(username);

		if (localUser == null) {
			throw new UsernameNotFoundException("User was not found");
		}

		return new User(localUser.getLogin(), localUser.getPassword(), localUser.getAuthorities());
	}
}
