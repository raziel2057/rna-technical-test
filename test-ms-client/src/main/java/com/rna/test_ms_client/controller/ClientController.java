package com.rna.test_ms_client.controller;

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

import com.rna.test_ms_client.model.Client;
import com.rna.test_ms_client.repository.ClientRepository;

@CrossOrigin(origins = "*/*")
@RestController
@RequestMapping("/api")
public class ClientController {
	@Autowired
	ClientRepository clientRepository;
	
	@GetMapping("/clients")
	public ResponseEntity<List<Client>> getAllClients(@RequestParam(required = false) String name) {
		try {
			System.out.println("Llega name:"+name);
			List<Client> clients = new ArrayList<Client>();
			if(Objects.nonNull(name)) {
				clientRepository.findByClientName(name).forEach(clients::add);
			} else {
				clientRepository.findAll().forEach(clients::add);
			}
			
			
			
			if (clients.isEmpty()) {
			    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(clients, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Error:"+e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/clients/{id}")
	public ResponseEntity<Client> getClientById(@PathVariable("id") long id) {
		try {
			Optional<Client> clientData = clientRepository.findById(id);
			
			if(clientData.isPresent()) {
				return new ResponseEntity<>(clientData.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/clients")
	public ResponseEntity<Client> createClient(@RequestBody Client client) {
	    try {
	    	Client _client = clientRepository
	          .save(new Client(client.getName(), client.getGender(), client.getAge(), client.getAddress(), client.getPhone(), client.getPassword(), Boolean.TRUE));
	    	return new ResponseEntity<>(_client, HttpStatus.CREATED);
	    } catch (Exception e) {
	    	e.printStackTrace();
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@PutMapping("/clients/{id}")
	public ResponseEntity<Client> updateClient(@PathVariable("id") long id, @RequestBody Client client) {
	    try {
	    	
	    	Optional<Client> clientData = clientRepository.findById(id);
	    	
	    	if(clientData.isPresent()) {
	    		Client _client = clientData.get();
	    		if(Objects.nonNull(client.getAddress())) {
	    			_client.setAddress(client.getAddress());
	    		}
	    		if(Objects.nonNull(client.getAge())) {
	    			_client.setAge(client.getAge());
	    		}
	    		if(Objects.nonNull(client.getGender())) {
	    			_client.setGender(client.getGender());
	    		}
	    		if(Objects.nonNull(client.getName())) {
	    			_client.setName(client.getName());
	    		}
	    		if(Objects.nonNull(client.getPassword())) {
	    			_client.setPassword(client.getPassword());
	    		}
	    		if(Objects.nonNull(client.getPhone())) {
	    			_client.setPhone(client.getPhone());
	    		}
	    		if(Objects.nonNull(client.isState())) {
	    			_client.setState(client.isState());
	    		}
	    		
	    		return new ResponseEntity<>(clientRepository.save(_client), HttpStatus.OK);
	    	} else {
	    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@DeleteMapping("/clients/{id}")
	public ResponseEntity<HttpStatus> deleteClient(@PathVariable("id") long id) {
	    try {
	    	clientRepository.deleteById(id);
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@DeleteMapping("/clients")
	public ResponseEntity<HttpStatus> deleteAllClients() {
	    try {
	    	clientRepository.deleteAll();
	    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	    	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	
	}
}
