package com.project.springSecurity.service;

import com.project.springSecurity.model.Users;

public interface UserService {

	Users getUserByUsername(String username);
	
	int saveUser(String username, String password);
}
