package br.com.edublockchain.transactionpool.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edublockchain.transactionpool.Transaction;
import br.com.edublockchain.transactionpool.dto.TransactionDTO;
import br.com.edublockchain.transactionpool.services.TransactionService;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

	@Autowired
	private TransactionService service;
	
	@PostMapping
	public ResponseEntity<Transaction> create(@RequestBody TransactionDTO dto) {
		return new ResponseEntity<Transaction>(service.create(dto), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<Set<Transaction>> getAll() {
		return new ResponseEntity<Set<Transaction>>(service.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{amount}")
	public ResponseEntity<Set<Transaction>> getN(@PathVariable Integer amount) {
		return new ResponseEntity<Set<Transaction>>(service.getN(amount), HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<?> remove(@RequestBody TransactionDTO dto) {
		if(service.remove(dto)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	
}
