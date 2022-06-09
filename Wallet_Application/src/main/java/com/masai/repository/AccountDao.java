package com.masai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masai.DTO.AccountDTO;
import com.masai.entity.Account;
import com.masai.entity.Wallet;


public interface AccountDao extends JpaRepository<Account, Integer>{
	public List<AccountDTO> findBywalletId(Wallet walletId);
	public Account findByAccountNo(String accountNo);
	
	
	
}
