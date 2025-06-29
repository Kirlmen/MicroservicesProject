package com.kirl.loans.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class LoansController {

	@GetMapping("hello")
	public String helloWorld(){
		return "Hello World";
	}
}
