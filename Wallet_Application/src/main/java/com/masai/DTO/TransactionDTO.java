package com.masai.DTO;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.masai.entity.Transaction;
import com.masai.entity.Wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDTO {
	
	private Integer transactionId;
	private String transactionType;
	private LocalDateTime dateTime;
	private String description;
	private Double amount;
	
	
	
	public TransactionDTO(Transaction t) {
		super();
		this.transactionId = t.getTransactionId();
		this.transactionType = t.getTransactionType();
		this.dateTime = t.getDateTime();
		this.description = t.getDescription();
		this.amount = t.getAmount();
	}
	
	
}
