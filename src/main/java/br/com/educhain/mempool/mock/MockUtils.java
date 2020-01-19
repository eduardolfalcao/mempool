package br.com.educhain.mempool.mock;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import br.com.educhain.mempool.Transaction;
import br.com.educhain.mempool.dto.TransactionDTO;
import br.com.educhain.mempool.services.TransactionService;

//disabling automatic startup of mockutils
//@Component
public class MockUtils {

	@Autowired
	private TransactionService service;
	
	private Base64.Encoder b64e = Base64.getEncoder();

	// class variable
	final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
	final static int DELAY = 60;
	final Random rand;

	public MockUtils(int seed) {
		this.rand = new Random(seed);
	}

	public MockUtils() {
		this.rand = new Random();
		this.mockTransactionCreation();
	}

	// consider using a Map<String,Boolean> to say whether the identifier is being
	// used or not
	final Set<String> identifiers = new HashSet<String>();

	public String randomIdentifier() {
		StringBuilder builder = new StringBuilder();
		while (builder.toString().length() == 0) {
			int length = rand.nextInt(5) + 5;
			for (int i = 0; i < length; i++) {
				builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
			}
			if (identifiers.contains(builder.toString())) {
				builder = new StringBuilder();
			}
		}
		return builder.toString();
	}

	public int randomValue(int limit) {
		return rand.nextInt(limit);
	}

	public int randomValue() {
		return rand.nextInt();
	}

	@Bean
	public void mockTransactionCreation() {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

		// creating 1000 keypairs
		List<KeyPair> keys = new ArrayList<KeyPair>();
		for (int i = 0; i < 1000; i++) {
			keys.add(KeyUtils.generateKeyPair(randomIdentifier()));
		}

		Runnable task = new Runnable() {
			public void run() {
				for (int i = 0; i < 5; i++) {
					int indexSender = randomValue(1000);
					double amount = randomValue(10000);
					double fee = randomValue(10000);

					Transaction t = new Transaction(keys.get(indexSender).getPublic().getEncoded(),
							keys.get(randomValue(1000)).getPublic().getEncoded(), amount, fee);
					
					byte[] signature = Signer.sign(t, keys.get(indexSender).getPrivate());

					TransactionDTO tDto = new TransactionDTO(t.getSender(), t.getReceiver(),
							amount, fee, t.getCreationTime(), t.getUniqueID(), signature);
					service.create(tDto);
				}
			}
		};
		scheduler.scheduleAtFixedRate(task, 0, DELAY, TimeUnit.SECONDS);
	}

}
