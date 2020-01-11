package br.com.educhain.mempool.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import br.com.educhain.mempool.Transaction;
import br.com.educhain.mempool.dto.TransactionDTO;

@Service
public class TransactionService {

	private static final Logger LOGGER = Logger.getLogger(TransactionService.class);

	private Map<String, Pair<Transaction, byte[]>> transactions = new HashMap<String, Pair<Transaction, byte[]>>();

	public Transaction create(TransactionDTO dto) {
		Transaction t = new Transaction(dto.getSender(), dto.getReceiver(), dto.getAmount(), dto.getFee());

		transactions.put(t.getUniqueID(), Pair.of(t, dto.getSignature()));
		LOGGER.info("Transaction and corresponding signature have just been added to the pool: <" + t + ", "
				+ dto.getSignature().hashCode() + ">");
		return t;
	}

	public Map<String, Pair<Transaction, byte[]>> getAll() {
		return transactions;
	}

	public Pair<Transaction, byte[]> remove(TransactionDTO dto) {
		Transaction t = new Transaction(dto.getSender(), dto.getReceiver(), dto.getAmount(), dto.getFee(), null,
				dto.getUniqueID());
		LOGGER.debug("Transaction is about to be removed from the pool: " + t);

		return transactions.remove(t.getUniqueID());
	}

	public Pair<Transaction, byte[]> remove(String uniqueID) {
		LOGGER.debug("Transaction with id " + uniqueID + " is about to be removed from the pool");
		return transactions.remove(uniqueID);
	}

	public Set<Pair<Transaction, byte[]>> getN(int amount) {

		Set<Pair<Transaction, byte[]>> nTransactions = new TreeSet<Pair<Transaction, byte[]>>();

		Iterator<Entry<String, Pair<Transaction, byte[]>>> it = transactions.entrySet().iterator();
		while (it.hasNext() && amount > 0) {
			nTransactions.add(it.next().getValue());
			amount--;
		}
		return nTransactions;
	}

}
