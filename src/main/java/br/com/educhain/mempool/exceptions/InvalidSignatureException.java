package br.com.educhain.mempool.exceptions;

import br.com.educhain.mempool.Transaction;

public class InvalidSignatureException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public InvalidSignatureException(Transaction t) {
		super("Transaction "+t+" could not be verified!");
	}

}
