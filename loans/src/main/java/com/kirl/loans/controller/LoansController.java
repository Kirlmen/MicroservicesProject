package com.kirl.loans.controller;

import com.kirl.loans.constants.LoansConstants;
import com.kirl.loans.dto.LoansDto;
import com.kirl.loans.dto.ResponseDto;
import com.kirl.loans.entity.Loans;
import com.kirl.loans.mapper.LoansMapper;
import com.kirl.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@RestController
public class LoansController {

	private ILoansService iLoansService;

	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createLoan(@RequestParam String mobileNumber) {
		iLoansService.createLoan(mobileNumber);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
	}

	@GetMapping("/fetch")
	public ResponseEntity<LoansDto> fetchLoan(@RequestParam String mobileNumber){
		LoansDto loansDto = iLoansService.fetchLoans(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(loansDto);
	}

}
