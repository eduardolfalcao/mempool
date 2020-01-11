package br.com.educhain.mempool.dto;

import java.security.PublicKey;
import java.util.Arrays;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class TransactionDTO {
	
	private PublicKey sender, receiver;
	private double amount, fee;
	private Date creationTime;
	
	private String uniqueID;	
	private byte[] signature;
	
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
		TransactionDTO other = (TransactionDTO) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (creationTime == null) {
			if (other.creationTime != null)
				return false;
		} else if (!creationTime.equals(other.creationTime))
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
		if (signature == null) {
			if (other.signature != null)
				return false;
		} else if (!Arrays.equals(signature,other.signature))
			return false;
		
		return true;
	}	
	
}
