package com.project.springSecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RandomController {
	
	@GetMapping("/hi")
	public String hello()
	{
		return "Hi Hello";
	}

}
