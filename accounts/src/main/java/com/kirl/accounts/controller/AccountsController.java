package com.kirl.accounts.controller;

import com.kirl.accounts.constants.AccountsConstants;
import com.kirl.accounts.dto.CustomerDto;
import com.kirl.accounts.dto.ResponseDto;
import com.kirl.accounts.service.IAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountsController {

	private IAccountsService iAccountsService;

	@Autowired
	public AccountsController(IAccountsService iAccountsService){
		this.iAccountsService = iAccountsService;
	}

	@PostMapping("/create")
	ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto){
		iAccountsService.createAccount(customerDto); //passing the data from the enduser to service layer.
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
	}

}
