package com.kirl.cards.dto;

import lombok.Data;

@Data
public class CardsDto {

	public String mobileNumber;

	public String cardNumber;

	public String cardType;

	public int totalLimit;

	public int amountUsed;

	public int availableAmount;


}
