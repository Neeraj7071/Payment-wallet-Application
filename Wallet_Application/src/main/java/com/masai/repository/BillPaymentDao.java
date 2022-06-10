package com.masai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.DTO.BillPaymentDTO;
import com.masai.entity.BillPayment;
import com.masai.entity.Wallet;

@Repository
public interface BillPaymentDao extends JpaRepository<BillPayment, Integer> {
    
	public List<BillPaymentDTO> findByWallet(Wallet w);
}
