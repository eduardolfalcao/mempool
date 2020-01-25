package br.com.educhain.mempool;

import java.io.Serializable;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.UUID;

import br.com.educhain.mempool.mock.KeyUtils;

public class Transaction implements Comparable<Transaction>, Serializable {

	private static final long serialVersionUID = -8270876610064570814L;
	private static KeyFactory keyFactory;

	static {
		try {
			keyFactory = KeyFactory.getInstance(KeyUtils.KEYGEN_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

//	public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);

	private byte[] sender, receiver, signature;
	private double amount, fee;

	private String uniqueID;

//	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone="UTC")
//	private Date creationTime;

	public Transaction(byte[] sender, byte[] receiver, byte[] signature, double amount, double fee) {
		this.sender = sender;
		this.receiver = receiver;
		this.signature = signature;
		this.amount = amount;
		this.fee = fee;

//		this.creationTime = new Date(System.currentTimeMillis());
		this.uniqueID = UUID.randomUUID().toString();
	}

	public Transaction(byte[] sender, byte[] receiver, byte[] signature, double amount, double fee, String uniqueID) {
		this(sender, receiver, signature, amount, fee);
//		this.creationTime = creationTime;
		this.uniqueID = uniqueID;
	}

	@Override
	public String toString() {
//		if (creationTime != null)
//			return "UniqueId: " + uniqueID + "; Sender: " + Arrays.hashCode(sender) + "; Receiver: " + Arrays.hashCode(receiver)
//					+ "; Amount: " + amount + "; " + "Fee: " + fee + "; Creation time: "
//					+ formatter.format(creationTime) + ";";
//		else
			return "UniqueId: " + uniqueID + "; Sender: " + Arrays.hashCode(sender) + "; Receiver: " + Arrays.hashCode(receiver)
					+ "; Amount: " + amount + "; " + "Fee: " + fee + ";";
	}

	public byte[] getSender() {
		return sender;
	}

	public void setSender(byte[] sender) {
		this.sender = sender;
	}

	public byte[] getReceiver() {
		return receiver;
	}

	public void setReceiver(byte[] receiver) {
		this.receiver = receiver;
	}

	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	public PublicKey getPubKey(byte[] key) {
		try {
			return keyFactory.generatePublic(new X509EncodedKeySpec(key));
		} catch (InvalidKeySpecException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

//	public Date getCreationTime() {
//		return creationTime;
//	}
//
//	public void setCreationTime(Date creationTime) {
//		this.creationTime = creationTime;
//	}

	public String getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	@Override
	public int hashCode() {
		return uniqueID.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (Double.doubleToLongBits(fee) != Double.doubleToLongBits(other.fee))
			return false;
		if (receiver == null) {
			if (other.receiver != null)
				return false;
		} else if (!Arrays.equals(receiver, other.receiver))
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!Arrays.equals(sender, other.sender))
			return false;
		if (signature == null) {
			if (other.signature != null)
				return false;
		} else if (!Arrays.equals(signature, other.signature))
			return false;
		if (uniqueID == null) {
			if (other.uniqueID != null)
				return false;
		} else if (!uniqueID.equals(other.uniqueID))
			return false;
		return true;
	}

	@Override
	public int compareTo(Transaction t) {
		if (this.fee < t.getFee())
			return 1;
		else if (this.fee > t.getFee())
			return -1;
		else {
			// transactions are only equal if they have the same uniqueID
			if (uniqueID.equals(t.getUniqueID()))
				return 0;
			else
				return 1;
		}
	}

}
