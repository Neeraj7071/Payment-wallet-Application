package com.masai.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.DTO.Date;
import com.masai.DTO.TransactionDTO;
import com.masai.entity.Transaction;
import com.masai.entity.UserSession;
import com.masai.globalExceptionHandler.CustomerNotFoundException;
import com.masai.repository.UserSessionDao;
import com.masai.repository.WalletDaoJpa;
import com.masai.service.TransactionServiceImpl;

@RestController
public class TransactionController {
	
	@Autowired
	private UserSessionDao userDao;
	
	@Autowired
	private TransactionServiceImpl trans;
	
	@Autowired
	private WalletDaoJpa wDao;
	
	@GetMapping("ViewAllTransactions")
	public ResponseEntity<List<TransactionDTO>> viewTransaction(@RequestParam("key") String key){
		UserSession user=userDao.findByUuid(key);
		if(user==null) {
			throw new CustomerNotFoundException("You are not authoraised person please login first.");
		}
		LocalDateTime prev=user.getDateTime();
		LocalDateTime date=LocalDateTime.now();
		if (prev.getDayOfMonth() != date.getDayOfMonth()) {
			userDao.delete(user);
			throw new CustomerNotFoundException("Your session is expired please login again");
		}
		
		return new ResponseEntity<>(trans.getTransaction(wDao.getById(user.getMobile())),HttpStatus.OK);
	}
	
	@GetMapping("transactionsByType")
	public ResponseEntity<List<TransactionDTO>> viewTransactionByType(@RequestParam("key") String key, @RequestBody String type){
		UserSession user=userDao.findByUuid(key);
		if(user==null) {
			throw new CustomerNotFoundException("You are not authoraised person please login first.");
		}
		LocalDateTime prev=user.getDateTime();
		LocalDateTime date=LocalDateTime.now();
		if (prev.getDayOfMonth() != date.getDayOfMonth()) {
			userDao.delete(user);
			throw new CustomerNotFoundException("Your session is expired please login again");
		}
		
		return new ResponseEntity<>(trans.getTransactionByType(type, wDao.getById(user.getMobile())) ,HttpStatus.OK);
	}
	
//	@GetMapping("transactionsByDate")
//	public ResponseEntity<List<Transaction>> viewTransactionByDate(@RequestParam("key") String key, @RequestBody Date datewise){
//		UserSession user=userDao.findByUuid(key);
//		if(user==null) {
//			throw new CustomerNotFoundException("You are not authoraised person please login first.");
//		}
//		LocalDateTime prev=user.getDateTime();
//		LocalDateTime date=LocalDateTime.now();
//		if (prev.getDayOfMonth() != date.getDayOfMonth()) {
//			userDao.delete(user);
//			throw new CustomerNotFoundException("Your session is expired please login again");
//		}
//		
//		return new ResponseEntity<>(trans.getTransactionByDate(datewise, wDao.getById(user.getMobile())) ,HttpStatus.OK);
//	}
	
	@PostMapping("transactions")
	public ResponseEntity<List<TransactionDTO>> viewTransactionByDate(@RequestParam("key") String key, @RequestBody Date datewise){
		UserSession user=userDao.findByUuid(key);
		if(user==null) {
			throw new CustomerNotFoundException("You are not authoraised person please login first.");
		}
		LocalDateTime prev=user.getDateTime();
		LocalDateTime date=LocalDateTime.now();
		if (prev.getDayOfMonth() != date.getDayOfMonth()) {
			userDao.delete(user);
			throw new CustomerNotFoundException("Your session is expired please login again");
		}
		
		return new ResponseEntity<>(trans.getTransactionByDate(datewise, wDao.getById(user.getMobile())),HttpStatus.OK);
	}

}
