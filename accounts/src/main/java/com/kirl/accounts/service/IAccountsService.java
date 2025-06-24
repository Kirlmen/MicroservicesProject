package com.kirl.accounts.service;

import com.kirl.accounts.dto.CustomerDto;

public interface IAccountsService {

	/**
	 *
	 * @param customerDto - CustomerDto Object
	 */
	void createAccount(CustomerDto customerDto);

}
