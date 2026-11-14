package com.project.springSecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.springSecurity.dto.AuthRequest;
import com.project.springSecurity.jwtService.JwtService;
import com.project.springSecurity.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping("/saveUser")
	public String saveUserWithEncodedPassword(@RequestParam String username, @RequestParam String password)
	{
		int response = userService.saveUser(username,password);
		
		if(response == 1)
		{
			return "User saved sucessfully";
		}
		return "User not saved";
	}
	
	@GetMapping("/hello")
	public String hello()
	{
		return "Hello";
	}
	
	@PostMapping("/authenticate")
	public void authenticate(@RequestBody AuthRequest authRequest)
	{
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), 
				authRequest.getPassword()));
		
		if(authentication.isAuthenticated())
		{
			String token = jwtService.generateToken(authRequest.getUsername());
			System.out.println("Token : " +token);
		}
	}

}
