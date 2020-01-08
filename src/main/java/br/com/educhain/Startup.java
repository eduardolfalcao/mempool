package br.com.educhain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.educhain.mempool.mock.MockUtils;

@SpringBootApplication
public class Startup {
	
	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);		
	}

}
