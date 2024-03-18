package com.ancora.teste.isbn.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ancora.teste.isbn.entities.SecurityUser;
import com.ancora.teste.isbn.repositories.UserRepository;

@Component
public class JpaUserDetailsServiceImpl implements UserDetailsService {

	@Autowired UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo
				.findByUserName(username)
				.map(SecurityUser::new)
				.orElseThrow(() -> 
					new UsernameNotFoundException("Usuário não encontrado"));
		
	}
	

}
