package com.kirl.accounts.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerDto {

	@NotEmpty(message = "Name field cannot be empty or null")
	@Size(min = 3, max = 10, message = "the length of customer name must be minimum 3 characters maximum 10")
	private String name;

	@NotEmpty(message = "Email field cannot be empty or null")
	@Email(message = "wrong type of email")
	private String email;

	@Pattern(regexp = "(^$|[0-9]{10})",message = "mobile number must have 11 digits")
	private String mobileNumber;

	private AccountsDto accountsDto;
}
