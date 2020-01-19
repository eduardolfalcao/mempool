package br.com.educhain.mempool.exceptions;

import br.com.educhain.mempool.Transaction;

public class InvalidSignatureException extends Exception{
	
	public InvalidSignatureException(Transaction t) {
		super("Transaction "+t+" could not be verified!");
	}

}
