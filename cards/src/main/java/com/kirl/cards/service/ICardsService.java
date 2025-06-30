package com.kirl.cards.service;

import com.kirl.cards.dto.CardsDto;

public interface ICardsService {


	/**
	 *
	 * @param mobileNumber- Mobile number of the Customer
	 */
	void createCards(String mobileNumber);
}
