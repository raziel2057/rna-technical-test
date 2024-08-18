package com.rna.test_ms_account.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rna.test_ms_account.model.Account;
import com.rna.test_ms_account.model.Message;
import com.rna.test_ms_account.model.Movement;
import com.rna.test_ms_account.repository.AccountRepository;
import com.rna.test_ms_account.repository.MovementRepository;

@CrossOrigin(origins = "*/*")
@RestController
@RequestMapping("/api")
public class MovementController {
	@Autowired
	MovementRepository movementRepository;
	@Autowired
	AccountRepository accountRepository;
	
	@GetMapping("/movements")
	public ResponseEntity<List<Movement>> getAllMovements(@RequestParam(required = false) Long accountNumber) {
		try {

			List<Movement> movements = new ArrayList<Movement>();
			if(Objects.nonNull(accountNumber)) {
				movementRepository.findMovementsByAccountNumber(accountNumber).forEach(movements::add);

			} else {
				movementRepository.findAll().forEach(movements::add);
			}
			
			
			
			if (movements.isEmpty()) {
			    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(movements, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Error:"+e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/movements/{id}")
	public ResponseEntity<Movement> getMovementById(@PathVariable("id") long id) {
		try {
			Optional<Movement> movementData = movementRepository.findById(id);
			
			if(movementData.isPresent()) {
				return new ResponseEntity<>(movementData.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/movements")
	public ResponseEntity<Movement> createMovement(@RequestBody Movement movement) {
	    try {
	    	Long accountNumber = movement.getAccount().getAccountNumber();
	    	Optional<Account> account = accountRepository.findById(accountNumber);
	    	
			
			if(account.isPresent()) {
				Account _account = account.get();
				BigDecimal actualBalance = movementRepository.findLastBalanceByAccountNumber(accountNumber);
				
				if(Objects.isNull(actualBalance)) {
					actualBalance = _account.getInitialBalance();
					//Register initial movement
			    	movementRepository.save(new Movement(LocalDate.now(), "CREDIT", actualBalance, 
			    			actualBalance, "INITIAL",accountNumber));
				}
				
				BigDecimal amount = movement.getAmount();
				String movementType =  movement.getMovementType();
				
				if("DEBIT".equals(movementType)) {
	    			if(amount.compareTo(actualBalance) == 1) {

						Movement _movement = new Movement.Builder().setMessage(new Message(1,"Saldo no disponible")).build();
						
						return new ResponseEntity<>(_movement, HttpStatus.BAD_REQUEST);
					}
	    			actualBalance = actualBalance.subtract(amount);
	    		}
	    		else if("CREDIT".equals(movementType)) {
	    			actualBalance = actualBalance.add(amount);
	    		}
	    		else {
	    			Movement _movement = new Movement.Builder().setMessage(new Message(2,"Solo se aceptan tipos de movimiento CREDIT o DEBIT")).build();
	    			return new ResponseEntity<>(_movement, HttpStatus.BAD_REQUEST);
	    		}
				
				Movement _movement = movementRepository.save(new Movement(LocalDate.now(), movementType, amount, 
						                                     actualBalance, "ACTIVE", accountNumber));
				
				return new ResponseEntity<>(_movement, HttpStatus.CREATED);
			} else {
				Movement _movement = new Movement.Builder().setMessage(new Message(3,"No se ha encontrado la cuenta: " + accountNumber)).build();
				return new ResponseEntity<>(_movement, HttpStatus.NOT_FOUND);
			}
	    } catch (Exception e) {
	      e.printStackTrace();
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@PutMapping("/movements/{id}")
	public ResponseEntity<List<Movement>> reverseMovement(@PathVariable("id") long id) {
	    try {
	    	
	    	Optional<Movement> movementData = movementRepository.findById(id);
	    	
	    	if(movementData.isPresent()) {
	    		Movement _movement = movementData.get();
	    		
	    		if("REVERSED".equals(_movement.getState())) {
	    			Movement _movementR = new Movement.Builder().setMessage(new Message(5,"No se puede reversar un movimiento ya reversado.")).build();
		    		List<Movement> movements = new ArrayList<Movement>();
		    		movements.add(_movementR);
		    		return new ResponseEntity<>(movements,HttpStatus.BAD_REQUEST);
	    		}
	    		
	    		_movement.setState("REVERSED");
	    		Movement reversedMovement = movementRepository.save(_movement);
	    		
	    		Long accountNumber = _movement.getAccount().getAccountNumber();
	    		String movementType = _movement.getMovementType();
	    		
	    		BigDecimal amount = _movement.getAmount();
	    		BigDecimal actualBalance = _movement.getBalance();
	    		
	    		if("CREDIT".equals(movementType)) {
	    			movementType = "DEBIT";
	    			actualBalance = actualBalance.subtract(amount);
	    		}
	    		else {
	    			movementType = "CREDIT";
	    			actualBalance = actualBalance.add(amount);
	    		}

	    		Movement newMovement = movementRepository.save(new Movement(LocalDate.now(), movementType, amount, 
                        actualBalance, "ACTIVE", accountNumber));
	    		
	    		List<Movement> movements = new ArrayList<Movement>();
	    		movements.add(reversedMovement);
	    		movements.add(newMovement);
	    		return new ResponseEntity<>(movements, HttpStatus.OK);
	    		
	    	} else {
	    		Movement _movement = new Movement.Builder().setMessage(new Message(4,"No se ha encontrado el movimiento: " + id)).build();
	    		List<Movement> movements = new ArrayList<Movement>();
	    		movements.add(_movement);
	    		return new ResponseEntity<>(movements,HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@DeleteMapping("/movements/{id}")
	public ResponseEntity<HttpStatus> deleteMovement(@PathVariable("id") long id) {
	    try {
	    	movementRepository.deleteById(id);
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@DeleteMapping("/movements")
	public ResponseEntity<HttpStatus> deleteAllMovements() {
	    try {
	    	movementRepository.deleteAll();
	    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	    	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	
	}
}
