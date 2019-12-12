package br.com.edublockchain.transactionpool.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	public ResponseEntity<Transaction> create(@RequestBody TransactionDTO t) {
		return new ResponseEntity<Transaction>(service.create(t), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<Set<Transaction>> findAll() {
		return new ResponseEntity<Set<Transaction>>(service.findAll(), HttpStatus.OK);
	}
	
}
