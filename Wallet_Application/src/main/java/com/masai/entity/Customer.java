package com.masai.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.masai.DTO.CustomerDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {
	
	@Id
	private String mobileNumber;
	private String name;
	private String password;
	@OneToOne(cascade = CascadeType.ALL,targetEntity = Wallet.class) 
	@JoinColumn(name="walletId")
	private Wallet wallet;
	
	public Customer(CustomerDTO c,Wallet w) {
		super();
		this.mobileNumber = c.getMobileNo();
		this.name = c.getName();
		this.password = c.getPassword();
		this.wallet = w;
	}
	
}
