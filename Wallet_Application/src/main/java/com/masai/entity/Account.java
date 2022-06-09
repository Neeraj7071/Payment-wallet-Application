package com.masai.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Value;

import com.masai.DTO.AccountDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class Account {
	
	@Id
	private String accountNo;
	private String ifscCode; 
	private String bankName;
	private double balance;
	@ManyToOne(cascade = CascadeType.ALL,targetEntity = Wallet.class) 
	@JoinColumn(name="walletId")
	private Wallet walletId;
	/**
	 * @param accountNo
	 * @param ifscCode
	 * @param bankName
	 * @param balance
	 * @param walletId
	 */
	public Account(AccountDTO a,Wallet walletId) {
		super();
		this.accountNo = a.getAccountNo();
		this.ifscCode = a.getIfscCode();
		this.bankName = a.getBankName();
		this.balance = a.getBalance();
		this.walletId = walletId;
	}



	

}
