package com.kirl.loans.controller;

import com.kirl.loans.constants.LoansConstants;
import com.kirl.loans.dto.ErrorResponseDto;
import com.kirl.loans.dto.LoansDto;
import com.kirl.loans.dto.ResponseDto;
import com.kirl.loans.entity.Loans;
import com.kirl.loans.mapper.LoansMapper;
import com.kirl.loans.service.ILoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@RestController
@Validated
public class LoansController {

	private ILoansService iLoansService;

	@Operation(
			summary = "Create Loan REST API",
			description = "REST API for creating a loan in the Bank DB with given mobile number")
	@ApiResponses({
			@ApiResponse(
					responseCode = "201",
					description = "HTTP Status CREATED"
			),
			@ApiResponse(
					responseCode = "400",
					description = "HTTP Status BAD REQUEST",
					content = @Content(
							schema = @Schema(implementation = ErrorResponseDto.class)
					)
			)
	})
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createLoan(@RequestParam
	                                                  @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
	                                                  String mobileNumber) {
		iLoansService.createLoan(mobileNumber);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
	}

	@GetMapping("/fetch")
	public ResponseEntity<LoansDto> fetchLoan(@RequestParam
	                                              @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
	                                              String mobileNumber){
		LoansDto loansDto = iLoansService.fetchLoans(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(loansDto);
	}

	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateLoan(@Valid @RequestBody LoansDto loansDto){
		boolean condition = iLoansService.updateLoans(loansDto);
		if(condition){
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
		}
		else {
			return ResponseEntity
					.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(LoansConstants.STATUS_417,LoansConstants.MESSAGE_417_UPDATE));
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteLoan(@RequestParam
	                                                  @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
	                                                  String mobileNumber){
		boolean condition = iLoansService.deleteLoans(mobileNumber);
		if(condition){
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
		}
		else {
			return ResponseEntity
					.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(LoansConstants.STATUS_417,LoansConstants.MESSAGE_417_DELETE));
		}
	}

}
