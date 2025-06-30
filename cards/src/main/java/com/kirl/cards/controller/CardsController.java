package com.kirl.cards.controller;

import com.kirl.cards.constants.CardsConstants;
import com.kirl.cards.dto.CardsDto;
import com.kirl.cards.dto.ResponseDto;
import com.kirl.cards.service.ICardsService;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class CardsController {

	ICardsService iCardsService;

	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createCards(@RequestParam
	                                               @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
	                                               String mobileNumber) {
		iCardsService.createCards(mobileNumber);
		return ResponseEntity.status(
						HttpStatus.CREATED)
				.body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));

	}

	@GetMapping("/fetch")
	public ResponseEntity<CardsDto> fetchCards(@RequestParam
	                                           @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
	                                           String mobileNumber) {
		return ResponseEntity.status(HttpStatus.OK).body(iCardsService.fetchCards(mobileNumber));
	}

	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateCards(@RequestBody CardsDto cardsDto) {
		boolean res = iCardsService.updateCards(cardsDto);
		if(res){
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
		}
		else{
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE)
			);
		}
	}
}
