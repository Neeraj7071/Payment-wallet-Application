package com.masai.DTO;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.masai.entity.Account;
import com.masai.entity.Wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountDTO {
	
	private String accountNo;
	private String ifscCode; 
	private String bankName;
	private double balance;

	public AccountDTO(Account a) {
		super();
		this.accountNo=a.getAccountNo();
		this.ifscCode=a.getIfscCode();
		this.bankName=a.getBankName();
		this.balance=a.getBalance();
	}
	
	
}
