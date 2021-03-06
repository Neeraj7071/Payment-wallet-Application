package com.masai.service;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

//import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.DTO.BillPaymentDTO;
import com.masai.entity.BillPayment;
import com.masai.entity.Customer;
import com.masai.entity.Transaction;
import com.masai.entity.Wallet;
import com.masai.globalExceptionHandler.CustomerNotFoundException;
import com.masai.globalExceptionHandler.InsufficientAmountException;
import com.masai.repository.BillPaymentDao;
import com.masai.repository.CustomerDao;
import com.masai.repository.TransactionDao;
import com.masai.repository.UserSessionDao;
import com.masai.repository.WalletDaoJpa;


	


@Service
public class BillPaymentServicesImpl implements BillPaymentServices {

	@Autowired
	private BillPaymentDao billpd;
	@Autowired
	private TransactionDao tDao;
	@Autowired
	private WalletDaoJpa wDao;

    
	
	@Override
	public BillPayment payBillPayment(BillPayment billPayment, String mob) {
		Wallet w=wDao.getById(mob);
		
		if(w.getBalance() < billPayment.getAmount()) {
			throw new InsufficientAmountException("Insufficient amount in wallet");
		}
		
		billPayment.setPaymentDate(LocalDateTime.now());
		Transaction myTransaction = new Transaction();
		myTransaction.setAmount(billPayment.getAmount());
		myTransaction.setDateTime(LocalDateTime.now());
		myTransaction.setDescription(billPayment.getBillType());
		myTransaction.setTransactionType("Debit");
		myTransaction.setWallet(w);
		tDao.save(myTransaction);
		w.setBalance(w.getBalance()-billPayment.getAmount());
		wDao.save(w);

		return billpd.save(billPayment);
	}







	@Override
	public List<BillPaymentDTO> viewBillPayment(Wallet w) throws CustomerNotFoundException {
        List<BillPaymentDTO> bill = billpd.findByWallet(w);
        return bill;
	}






	
	
	

	
	
	

	

	
	
	
}
