package com.kirl.accounts.service;

import com.kirl.accounts.dto.CustomerDto;

public interface IAccountsService {

	/**
	 *
	 * @param customerDto - CustomerDto Object
	 */
	void createAccount(CustomerDto customerDto);

	/**
	 *
	 * @param mobileNumber-Input Mobile number
	 * @return account details based on a mobile number
	 */
	CustomerDto fetchAccount(String mobileNumber);

}
