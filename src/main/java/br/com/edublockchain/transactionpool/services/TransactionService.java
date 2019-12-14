package br.com.edublockchain.transactionpool.services;

import java.util.Iterator;
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
		if (transactions.add(t)) {
			System.out.println(t);
			return t;
		}
		else
			return null;
	}

	public Set<Transaction> getAll() {
		return transactions;
	}

	public boolean remove(TransactionDTO dto) {
		Transaction t = new Transaction(dto.getSender(), dto.getReceiver(), dto.getAmount(), dto.getFee(),
				dto.getCreationTime());
		
		System.out.println("******: "+t);
		
		return transactions.remove(t);
	}

	public Set<Transaction> getN(int amount) {
		
		Set<Transaction> nTransactions = new TreeSet<Transaction>();
		
		Iterator<Transaction> it = transactions.iterator();
		while(it.hasNext() && amount > 0) {
			nTransactions.add(it.next());
			amount--;
		}
		return nTransactions;
	}

}
