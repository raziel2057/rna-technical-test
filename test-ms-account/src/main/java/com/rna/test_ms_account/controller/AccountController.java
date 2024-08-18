package com.rna.test_ms_account.controller;

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
import com.rna.test_ms_account.repository.PersonRepository;

@CrossOrigin(origins = "*/*")
@RestController
@RequestMapping("/api")
public class AccountController {
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	PersonRepository personRepository;
	@Autowired
	MovementRepository movementRepository;
	
	@GetMapping("/accounts")
	public ResponseEntity<List<Account>> getAllAccounts(@RequestParam(required = false) String name) {
		try {

			List<Account> accounts = new ArrayList<Account>();
			if(Objects.nonNull(name)) {
				Long identification = personRepository.findIdentificationByBame(name);
				if(Objects.isNull(identification)) {
					return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
				}
				accountRepository.findAccountsByIdentification(identification).forEach(accounts::add);

			} else {
				accountRepository.findAll().forEach(accounts::add);
			}
			
			
			
			if (accounts.isEmpty()) {
			    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(accounts, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Error:"+e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/accounts/{id}")
	public ResponseEntity<Account> getAccountById(@PathVariable("id") long id) {
		try {
			Optional<Account> accountData = accountRepository.findById(id);
			
			if(accountData.isPresent()) {
				return new ResponseEntity<>(accountData.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/accounts")
	public ResponseEntity<Account> createAccount(@RequestBody Account account) {
	    try {
	    	Long identification = personRepository.findIdentificationByBame(account.getClientName());
	    	System.out.println("identification: "+identification);
			if(Objects.isNull(identification)) {
				return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
			}
	    	Account _account = accountRepository
	          .save(new Account(account.getAccountNumber(),account.getAccountType(),account.getInitialBalance(),Boolean.TRUE,identification));
	    	//Register initial movement
	    	Movement _movement = movementRepository.save(new Movement(LocalDate.now(), "CREDIT", account.getInitialBalance(), 
	    			account.getInitialBalance(), "INITIAL", account.getAccountNumber()));
	    	
	    	if(Objects.isNull(_movement)) {
	    		accountRepository.delete(_account);
	    		_account = new Account.Builder().setMessage(new Message(1,"No fue posible registrar el depósito inicial")).build();
	    	}
	    	
	      return new ResponseEntity<>(_account, HttpStatus.CREATED);
	    } catch (Exception e) {
	      e.printStackTrace();
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@PutMapping("/accounts/{id}")
	public ResponseEntity<Account> updateAccount(@PathVariable("id") long id, @RequestBody Account account) {
	    try {
	    	
	    	Optional<Account> accountData = accountRepository.findById(id);
	    	
	    	if(accountData.isPresent()) {
	    		Account _account = accountData.get();
	    		if(Objects.nonNull(account.getAccountType())) {
	    			_account.setAccountType(account.getAccountType());
	    		}
	    		if(Objects.nonNull(account.getState())) {
	    			_account.setState(account.getState());
	    		}

	    		return new ResponseEntity<>(accountRepository.save(_account), HttpStatus.OK);
	    	} else {
	    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@DeleteMapping("/accounts/{id}")
	public ResponseEntity<HttpStatus> deleteAccount(@PathVariable("id") long id) {
	    try {
	    	accountRepository.deleteById(id);
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@DeleteMapping("/accounts")
	public ResponseEntity<HttpStatus> deleteAllAccounts() {
	    try {
	    	accountRepository.deleteAll();
	    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	    	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	
	}
}
