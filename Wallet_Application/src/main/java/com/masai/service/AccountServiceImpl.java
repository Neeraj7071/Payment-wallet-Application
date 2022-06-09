package com.masai.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.AccountException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.DTO.AccountDTO;
import com.masai.entity.Account;
import com.masai.entity.Transaction;
import com.masai.entity.Wallet;
import com.masai.repository.AccountDao;
import com.masai.repository.TransactionDao;
import com.masai.repository.WalletDaoJpa;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountDao aDao;
	
	@Autowired
	private WalletDaoJpa wDao;
	
	@Autowired
	private TransactionDao tDao;
	
	@Override
	public AccountDTO addAcount(Account account,Wallet wallet) {
		wDao.save(wallet);
		return new AccountDTO(aDao.save(account));
	}

	@Override
	public AccountDTO deleteAccountByAccountNo(String accountId,Wallet wallet) throws AccountException {
		Account existingAccount= aDao.findByAccountNo(accountId);
		if(existingAccount.getWalletId()==wallet) {
			existingAccount.setWalletId(null);
			aDao.save(existingAccount);
			aDao.delete(existingAccount);
			return new AccountDTO(existingAccount);			
		}
		else
			throw new AccountException("No Account available please this accountNo with your wallet");
	}


	@Override
	public List<String> getAccountsByWalletId(Wallet walletId) throws AccountException {
		List<AccountDTO> accounts=aDao.findBywalletId(walletId);
		if(accounts.isEmpty()) {
			throw new AccountException("No Account available please add account with your wallet");
		}
		else {
			List<String> aString=new ArrayList<String>();
			accounts.forEach((n)->aString.add(n.getAccountNo()));
			return aString;
		}
	}

	@Override
	public AccountDTO getAccountByAccountNo(String accountNo, Wallet wallet) throws AccountException {
		Account a=aDao.findByAccountNo(accountNo);
		if(a.getWalletId()==wallet)
			return new AccountDTO(a);
		else
			throw new AccountException("No Account available please this accountNo with your wallet");
	}

	@Override
	public AccountDTO removeMoneyFromAccount(String accountNo, Wallet wallet, double money) throws AccountException {
		Account a=aDao.findByAccountNo(accountNo);
		if(a.getWalletId()==wallet) {
			if(a.getBalance()>=money) {
				wallet.setBalance(wallet.getBalance()+money);
				wDao.save(wallet);
				a.setBalance(a.getBalance()-money);
				Transaction myTransaction = new Transaction();
				myTransaction.setAmount(money);
				myTransaction.setDateTime(LocalDateTime.now());
				myTransaction.setDescription(accountNo+" Transfer Money to wallet "+wallet.getNumber());
				myTransaction.setTransactionType("Debit");
				myTransaction.setWallet(wallet);
				tDao.save(myTransaction);
				return new AccountDTO(aDao.save(a));				
			}
			
			else
				throw new AccountException("insufficient balance in your account please select another account");
		}
		else
			throw new AccountException("No Account available please this accountNo with your wallet");
	}

	

//	@Override
//	public Account addMoneyInAccount(String accountNo, double money) throws AccountException {
//		Account a=aDao.findByAccountNo(accountNo);
//		if(a==null)
//			throw new AccountException("No Account available please this accountNo with your Number");
//		a.setBalance(a.getBalance()+money);
//		aDao.save(a);
//		return a;
//	}


	@Override
	public AccountDTO sendMoneyInAccount(String accountNo1, String accountNo2, Wallet wallet, double money) throws AccountException {
		Account a=aDao.findByAccountNo(accountNo1);
		if(a==null)
			throw new AccountException("No Account available please this accountNo ");
		if(a.getWalletId()==wallet) {
			if(a.getBalance()>=money) {
				Account a1=aDao.findByAccountNo(accountNo2);
				if(a==null)
					throw new AccountException("No Account available please this accountNo ");
				a1.setBalance(a1.getBalance()+money);
				a.setBalance(a.getBalance()-money);
				Transaction myTransaction = new Transaction();
				myTransaction.setAmount(money);
				myTransaction.setDateTime(LocalDateTime.now());
				myTransaction.setDescription(accountNo1+" Transfer Money to Account Number "+accountNo2);
				myTransaction.setTransactionType("Debit");
				myTransaction.setWallet(wallet);
				tDao.save(myTransaction);
				Transaction myTransaction1 = new Transaction();
				myTransaction1.setAmount(money);
				myTransaction1.setDateTime(LocalDateTime.now());
				myTransaction1.setDescription(money+" Money recieve from wallet "+wallet.getNumber());
				myTransaction1.setTransactionType("Credit");
				Account aa=aDao.findByAccountNo(accountNo2);
				myTransaction1.setWallet(aa.getWalletId());
				tDao.save(myTransaction1);
				aDao.save(a1);
				return new AccountDTO(aDao.save(a));				
			}
			else
				throw new AccountException("insufficient balance in your account please select another account");
		}
		else
			throw new AccountException("No Account available please this accountNo with your wallet");
	}

//	@Override
//	public Account removeMoneyFromAccountAndAddOtherWallet(String accountNo, Wallet wallet1, Wallet wallet2,
//			double money) throws AccountException {
//		System.out.println(accountNo);
//		Account a=aDao.findByAccountNo(accountNo);
//		if(a.getWalletId()==wallet1) {
//			if(a.getBalance()>=money) {
//				wallet2.setBalance(wallet2.getBalance()+money);
//				wDao.save(wallet2);
//				a.setBalance(a.getBalance()-money);
//				return aDao.save(a);				
//			}
//			
//			else
//				throw new AccountException("insufficient balance in your account please select another account");
//		}
//		else
//			throw new AccountException("No Account available please this accountNo with your wallet");
//	}
//
//	@Override
//	public Account sendMoneyUsingMobileNo(Wallet wallet1, Wallet Wallet2, double money) throws AccountException {
//		if(wallet1.getBank()==null)
//			throw new AccountException("No Account available your MobileNo please add account");
//		Account a=aDao.findByAccountNo(wallet1.getBank());
//			if(a.getBalance()>=money) {
//				Account a1=aDao.findByAccountNo(Wallet2.getBank());
//				if(Wallet2.getBank()==null)
//					throw new AccountException("No Account available recvier MobileNo ");
//				a1.setBalance(a1.getBalance()+money);
//				a.setBalance(a.getBalance()-money);
//				aDao.save(a1);
//				return aDao.save(a);				
//			}
//			else
//				throw new AccountException("insufficient balance in your account please select another account");
//	}

	








}


