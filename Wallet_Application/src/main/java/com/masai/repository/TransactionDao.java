package com.masai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.DTO.TransactionDTO;
import com.masai.entity.Transaction;
import com.masai.entity.Wallet;
@Repository
public interface TransactionDao  extends JpaRepository<Transaction, Integer> {
	
	public List<TransactionDTO> findByWallet(Wallet w);
}
