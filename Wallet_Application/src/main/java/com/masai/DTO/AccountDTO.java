package com.masai.DTO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
	
//	@NotBlank(message = "Account Number is mandatory")
//	@Min(value=5, message="Account Number Minimum length should be 6")
//	@Max(value=21, message="Account Number Maximum length should be 20")
	@Pattern(regexp = "^[0-9]{6,20}",message="Invalid Account Number Foramateaa")
	private String accountNo;
//	@NotBlank(message = "IFSC code is mandatory")
//	@Min(value=9, message="IFSC code  length should be 10")
//	@Max(value=11, message="IFSC code  length should be 10")
	@Pattern(regexp = "^[a-zA-Z]{4}[0-9]{6}",message="Invalid IFSC Foramate [A-Z]{4}[0-9]{6}")
	private String ifscCode;
	@NotBlank(message = "Bank Name is mandatory")
	@Size(min = 3,max=30, message="Bank Name Minimum length should be 3")
	@Pattern(regexp = "^[a-zA-Z]{3,30}",message="Invalid Account name Foramate")
	private String bankName;
	@NotNull
	private double balance;

	public AccountDTO(Account a) {
		super();
		this.accountNo=a.getAccountNo();
		this.ifscCode=a.getIfscCode();
		this.bankName=a.getBankName();
		this.balance=a.getBalance();
	}
	
	
}
