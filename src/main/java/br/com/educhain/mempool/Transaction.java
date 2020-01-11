package br.com.educhain.mempool;

import java.io.Serializable;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Transaction implements Comparable<Transaction>, Serializable {

	private static final long serialVersionUID = -8270876610064570814L;

	public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	private PublicKey sender, receiver;
	private double amount, fee;

	private String uniqueID;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date creationTime;

	public Transaction(PublicKey sender, PublicKey receiver, double amount, double fee) {
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
		this.fee = fee;

		this.creationTime = new Date(System.currentTimeMillis());
		this.uniqueID = UUID.randomUUID().toString();
	}

	public Transaction(PublicKey sender, PublicKey receiver, double amount, double fee, Date creationTime,
			String uniqueID) {
		this(sender, receiver, amount, fee);
		this.creationTime = creationTime;
		this.uniqueID = uniqueID;
	}

	@Override
	public String toString() {
		if (creationTime != null)
			return "UniqueId: " + uniqueID + "; Sender: " + sender.hashCode() + "; Receiver: " + receiver.hashCode()
					+ "; Amount: " + amount + "; " + "Fee: " + fee + "; Creation time: "
					+ formatter.format(creationTime) + ";";
		else
			return "UniqueId: " + uniqueID + "; Sender: " + sender.hashCode() + "; Receiver: " + receiver.hashCode() + "; Amount: " + amount
					+ "; " + "Fee: " + fee + ";";
	}

	public PublicKey getSender() {
		return sender;
	}

	public void setSender(PublicKey sender) {
		this.sender = sender;
	}

	public PublicKey getReceiver() {
		return receiver;
	}

	public void setReceiver(PublicKey receiver) {
		this.receiver = receiver;
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

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

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
		} else if (!receiver.equals(other.receiver))
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
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
