package br.com.edublockchain.transactionpool.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class TransactionDTO {
	
	private String sender;
	private String receiver;
	private double amount, fee;
	private Date creationTime;
	
}
