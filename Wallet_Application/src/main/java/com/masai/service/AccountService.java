package com.masai.service;

import java.util.List;

import javax.security.auth.login.AccountException;

import com.masai.DTO.AccountDTO;
import com.masai.entity.Account;
import com.masai.entity.Wallet;

public interface AccountService {

	public AccountDTO addAcount(Account Account,Wallet wallet);
	
	public List<String> getAccountsByWalletId(Wallet walletId) throws AccountException ;

	public AccountDTO deleteAccountByAccountNo(String accountId,Wallet wallet)throws AccountException;
	
	public AccountDTO getAccountByAccountNo(String accountNo,Wallet wallet)throws AccountException;
	
	public AccountDTO removeMoneyFromAccount(String accountNo,Wallet wallet,double money) throws AccountException ;
	
//	public Account addMoneyInAccount(String accountNo,double money) throws AccountException ;
	
	public AccountDTO sendMoneyInAccount(String accountNo1,String accountNo2,Wallet wallet,double money) throws AccountException ;

//	public Account removeMoneyFromAccountAndAddOtherWallet(String accountNo,Wallet wallet1,Wallet wallet2,double money) throws AccountException ;
	
//	public Account sendMoneyUsingMobileNo(Wallet wallet1,Wallet Wallet2,double money) throws AccountException ;
	
}



