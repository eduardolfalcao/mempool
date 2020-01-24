package br.com.educhain.mempool.mock;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.Base64;

import org.apache.log4j.Logger;

import br.com.educhain.mempool.Transaction;

public class Signer {
	
	private static final Logger LOGGER = Logger.getLogger(Signer.class);
	
	public static byte[] sign(Transaction trans, PrivateKey privKey) {

		Signature sign;
		byte[] signature = null;
		try {
			sign = Signature.getInstance("SHA256withDSA");
			sign.initSign(privKey);

			byte[] transBytes = Signer.convertTransactionToByteArray(trans);
			sign.update(transBytes);

			signature = sign.sign();
			LOGGER.debug("Transaction " + trans + " had just been signed with privkey(hashCode) " + privKey.hashCode());
			LOGGER.info("Transaction had just been signed.");
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			e.printStackTrace();
		}

		return signature;
	}
	
	public static boolean verify(Transaction trans) {
		
		try {
			Signature sign = Signature.getInstance("SHA256withDSA");
			PublicKey key = trans.getPubKey(trans.getSender());			
			sign.initVerify(key);
			sign.update(convertTransactionToByteArray(trans));			
			boolean result = sign.verify(trans.getSignature());
			if (result) {
				LOGGER.debug("Transaction " + trans + " had just been verified.");
				LOGGER.info("Transaction had just been verified.");
			} else {
				LOGGER.debug("Transaction " + trans + " couldn't be verified.");
				LOGGER.info("Transaction couldn't be verified.");
			}
			return result;
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static byte[] convertTransactionToByteArray(Transaction trans) {
		System.out.println("Trans: "+trans);
		return trans.toString().getBytes();
	}

}
