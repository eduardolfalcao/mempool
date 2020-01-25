package br.com.educhain.mempool.services;

import java.util.Base64;
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
import br.com.educhain.mempool.exceptions.InvalidSignatureException;
import br.com.educhain.mempool.mock.Signer;

@Service
public class TransactionService {

	private static final Logger LOGGER = Logger.getLogger(TransactionService.class);

	private Map<String, Pair<Transaction, byte[]>> transactions = new HashMap<String, Pair<Transaction, byte[]>>();

	public Transaction create(TransactionDTO dto) throws InvalidSignatureException {

		Transaction t = new Transaction(Base64.getDecoder().decode(dto.getSender()),
				Base64.getDecoder().decode(dto.getReceiver()), Base64.getDecoder().decode(dto.getSignature()),
				dto.getAmount(), dto.getFee(), dto.getCreationTime(), dto.getUniqueID());

		if (Signer.verify(t)) {
			transactions.put(t.getUniqueID(), Pair.of(t, dto.getSignature().getBytes()));
			LOGGER.info("Transaction and corresponding signature have just been added to the pool: <" + t + ", "
					+ dto.getSignature().hashCode() + ">");
			return t;
		} else {
			throw new InvalidSignatureException(t);
		}
	}

	public Map<String, Pair<Transaction, byte[]>> getAll() {
		return transactions;
	}

	public Pair<Transaction, byte[]> remove(TransactionDTO dto) {
		Transaction t = new Transaction(dto.getSender().getBytes(), dto.getReceiver().getBytes(),
				dto.getSignature().getBytes(), dto.getAmount(), dto.getFee(), dto.getCreationTime(), dto.getUniqueID());
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
