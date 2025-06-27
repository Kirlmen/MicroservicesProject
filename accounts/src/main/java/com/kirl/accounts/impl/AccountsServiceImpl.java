package com.kirl.accounts.impl;

import com.kirl.accounts.constants.AccountsConstants;
import com.kirl.accounts.dto.CustomerDto;
import com.kirl.accounts.entity.Accounts;
import com.kirl.accounts.entity.Customer;
import com.kirl.accounts.exception.CustomerAlreadyExistException;
import com.kirl.accounts.mapper.AccountsMapper;
import com.kirl.accounts.mapper.CustomerMapper;
import com.kirl.accounts.repository.AccountsRepository;
import com.kirl.accounts.repository.CustomerRepository;
import com.kirl.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {
	AccountsRepository accountsRepository;
	CustomerRepository customerRepository;


	@Override
	public void createAccount(CustomerDto customerDto) {
		Customer customer = CustomerMapper.mapToCustomer(customerDto,new Customer());
		Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());

		if(optionalCustomer.isPresent()){
			throw new CustomerAlreadyExistException("Customer already registered with given mobileNumber " + customerDto.getMobileNumber());
		}
		customer.setCreatedAt(LocalDateTime.now());
		customer.setCreatedBy("Anonymous");
		Customer savedCustomer = customerRepository.save(customer); //pullin the id of the customer who saved
		accountsRepository.save(createNewAccount(savedCustomer));
	}

	/**
	 *
	 * @param customer - Customer object
	 * @return new account details
	 */
	private Accounts createNewAccount(Customer customer){
		Accounts newAccount = new Accounts();
		newAccount.setCustomerId(customer.getCustomerId());
		long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

		newAccount.setAccountType(AccountsConstants.SAVINGS);
		newAccount.setBranchAddress(AccountsConstants.ADDRESS);
		newAccount.setAccountNumber(randomAccNumber);

		newAccount.setCreatedBy("Anonymous");
		newAccount.setCreatedAt(LocalDateTime.now());

		return newAccount;
	}
}
