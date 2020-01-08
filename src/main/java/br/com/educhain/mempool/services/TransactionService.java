package br.com.educhain.mempool.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import br.com.educhain.mempool.Transaction;
import br.com.educhain.mempool.dto.TransactionDTO;

@Service
public class TransactionService {

	static Logger logger = Logger.getLogger(TransactionService.class);

	private Map<String, Transaction> transactions = new HashMap<String, Transaction>();

	public Transaction create(TransactionDTO dto) {
		Transaction t = new Transaction(dto.getSender(), dto.getReceiver(), dto.getAmount(), dto.getFee(),
				dto.getPubKey(), dto.getSignature());

		transactions.put(t.getUniqueID(), t);
		logger.info("Transaction has just been added to the pool: " + t);
		return t;
	}

	public Map<String, Transaction> getAll() {
		return transactions;
	}

	public Transaction remove(TransactionDTO dto) {
		Transaction t = new Transaction(dto.getSender(), dto.getReceiver(), dto.getAmount(), dto.getFee(), null,
				dto.getUniqueID());
		logger.debug("Transaction is about to be removed from the pool: " + t);

		return transactions.remove(t.getUniqueID());
	}

	public Transaction remove(String uniqueID) {
		logger.debug("Transaction with id " + uniqueID + " is about to be removed from the pool");
		return transactions.remove(uniqueID);
	}

	public Set<Transaction> getN(int amount) {

		Set<Transaction> nTransactions = new TreeSet<Transaction>();

		Iterator<Entry<String, Transaction>> it = transactions.entrySet().iterator();
		while (it.hasNext() && amount > 0) {
			nTransactions.add(it.next().getValue());
			amount--;
		}
		return nTransactions;
	}

}
