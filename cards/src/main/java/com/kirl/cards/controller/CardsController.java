package com.kirl.cards.controller;

import com.kirl.cards.constants.CardsConstants;
import com.kirl.cards.dto.CardsDto;
import com.kirl.cards.dto.ResponseDto;
import com.kirl.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class CardsController {

	ICardsService iCardsService;

	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createCards(@RequestParam String mobileNumber){
		iCardsService.createCards(mobileNumber);
		return ResponseEntity.status(
				HttpStatus.CREATED)
				.body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));

	}
}
