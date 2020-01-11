package br.com.educhain.mempool.mock;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

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
			LOGGER.debug("Transaction "+trans+" had just been signed with privkey(hashCode) "+privKey.hashCode());
			LOGGER.info("Transaction had just been signed.");
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			e.printStackTrace();
		}
				
		return signature;
	}
	
	private static byte[] convertTransactionToByteArray(Transaction trans) {
		byte[] transInBytes = null;
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
          out = new ObjectOutputStream(bos);   
          out.writeObject(trans);
          out.flush();
          transInBytes = bos.toByteArray();
        } catch (IOException e) {
			e.printStackTrace();
		} finally {
          try {
            bos.close();
          } catch (IOException ex) {}
        }
        
        return transInBytes;
	}

}
