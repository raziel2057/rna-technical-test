package com.rna.test_ms_account.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rna.test_ms_account.DTO.AccountStatementDTO;
import com.rna.test_ms_account.model.Message;
import com.rna.test_ms_account.repository.MovementRepository;

@CrossOrigin(origins = "*/*")
@RestController
@RequestMapping("/api")
public class AccountStatementController {
	@Autowired
	MovementRepository movementRepository;
	
	@GetMapping("/reports")
	public ResponseEntity<List<AccountStatementDTO>> getAccountStatement(@RequestParam(required = true) String clientName,
																		@RequestParam(required = true) LocalDate initialDate,
																		@RequestParam(required = true) LocalDate finishDate) {
		try {
			
			System.out.println("clientName: " + clientName);
			System.out.println("initialDate: " + initialDate);
			System.out.println("finishDate: " + finishDate);
			
			List<AccountStatementDTO> movementsResponse = new ArrayList<AccountStatementDTO>();
			if(Objects.nonNull(clientName) && Objects.nonNull(initialDate) && Objects.nonNull(finishDate)) {
				movementRepository.getAccountStatement(clientName, initialDate, finishDate).forEach(movementsResponse::add);
			} else {
				AccountStatementDTO _accountStatementDTO = new AccountStatementDTO.Builder()
						.setMessage(new Message(1,"No se enviaron los par[ametros requeridos.")).build();
				movementsResponse.add(_accountStatementDTO);
				return new ResponseEntity<>(movementsResponse,HttpStatus.BAD_REQUEST);
			}
				
			if (movementsResponse.isEmpty()) {
			    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(movementsResponse, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Error:"+e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
