package com.masai.entity;


import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer transactionId;
	private String transactionType;
	private LocalDateTime dateTime;
	private String description;
	private Double amount;
	@ManyToOne(targetEntity = Wallet.class,cascade = CascadeType.ALL)
	@JoinColumn(name="walletId")
	private Wallet wallet;
}
