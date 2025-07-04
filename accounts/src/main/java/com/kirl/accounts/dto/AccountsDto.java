package com.kirl.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(name = "Accounts", description = "Schema to holds the Account information")
public class AccountsDto {

	@NotEmpty(message = "AccountNumber can not be a null or empty")
	@Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
	@Schema(description = "Account number of the EazyBank account")
	private Long accountNumber;

	@NotEmpty(message = "AccountType can not be a null or empty")
	@Schema(description = "Account type of the EazyBank account", example = "Savings")
	private String accountType;

	@NotEmpty(message = "BranchAddress can not be a null or empty")
	@Schema(description = "branch address of the EazyBank account", example = "123 Street No:24 NY,NY")
	private String branchAddress;
}
