package com.project.springSecurity.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.springSecurity.model.Users;
import com.project.springSecurity.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService,UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Users getUserByUsername(String username) {

		return userRepository
				.findByUsernameAndIsActive(username, 'Y')
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users user = getUserByUsername(username);
		
		return User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.authorities(Collections.emptyList())
				.build();
	}

	@Override
	public int saveUser(String username, String password) {

		Users userDetails = new Users();
		userDetails.setPassword(passwordEncoder.encode(password));
		userDetails.setUsername(username);
		userDetails.setIsActive('Y');
		userRepository.save(userDetails);
		
		return 1;
	}

}
