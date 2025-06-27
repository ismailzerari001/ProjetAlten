package com.altenecommerce.service;

import com.altenecommerce.entity.User;
import com.altenecommerce.repository.UserJsonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserJsonRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    User user = userRepository.findByEmail(email)
	                 .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

	    return org.springframework.security.core.userdetails.User.builder()
	            .username(user.getEmail())
	            .password(user.getPassword())
	            .authorities(Collections.emptyList()) // Rôles vides pour le moment
	            .build();
	}
}
