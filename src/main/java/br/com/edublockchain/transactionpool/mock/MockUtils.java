package br.com.edublockchain.transactionpool.mock;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import br.com.edublockchain.transactionpool.dto.TransactionDTO;
import br.com.edublockchain.transactionpool.services.TransactionService;

@Component
public class MockUtils {
	
	@Autowired
	private TransactionService service;
	
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

	// consider using a Map<String,Boolean> to say whether the identifier is being used or not 
	final Set<String> identifiers = new HashSet<String>();

	public String randomIdentifier() {
	    StringBuilder builder = new StringBuilder();
	    while(builder.toString().length() == 0) {
	        int length = rand.nextInt(5)+5;
	        for(int i = 0; i < length; i++) {
	            builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
	        }
	        if(identifiers.contains(builder.toString())) {
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

		Runnable task = new Runnable() {
			public void run() {
				for (int i = 0; i < 20; i++) {
					TransactionDTO tDto = new TransactionDTO(
							randomIdentifier(), 
							randomIdentifier(),
							randomValue(10000),
							randomValue(10000),
							null,
							null);
					service.create(tDto);
				}
			}
		};		
		scheduler.scheduleAtFixedRate(task, 0, DELAY, TimeUnit.SECONDS);
	}

}
