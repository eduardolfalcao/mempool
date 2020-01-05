package br.com.edublockchain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.edublockchain.transactionpool.mock.MockUtils;

@SpringBootApplication
public class Startup {
	
	public static void main(String[] args) {
		//new MockUtils();
		SpringApplication.run(Startup.class, args);		
	}

}
