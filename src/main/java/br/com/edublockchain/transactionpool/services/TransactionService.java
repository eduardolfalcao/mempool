package br.com.edublockchain.transactionpool.services;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import br.com.edublockchain.transactionpool.Transaction;
import br.com.edublockchain.transactionpool.dto.TransactionDTO;

@Service
public class TransactionService {
	
	private Set<Transaction> transactions = new TreeSet<Transaction>();
	
	public Transaction create(TransactionDTO dto) {
		Transaction t = new Transaction(dto.getSender(), dto.getReceiver(), dto.getAmount(), dto.getFee());
		if(transactions.add(t))
			return t;
		else 
			return null;
	}
	
	public Set<Transaction> findAll(){
		return transactions;
	}

}
