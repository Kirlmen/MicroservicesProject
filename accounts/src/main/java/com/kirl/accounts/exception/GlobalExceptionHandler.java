package com.kirl.accounts.exception;

import com.kirl.accounts.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
	ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistException(
			CustomerAlreadyExistException exception,
			WebRequest webRequest){
		ErrorResponseDto errorResponseDto = new ErrorResponseDto(
				webRequest.getDescription(false), //this will send only apiPath which our DTO Class needs.
				HttpStatus.BAD_REQUEST,
				exception.getMessage(),
				LocalDateTime.now()
		);
		return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
	}
}
