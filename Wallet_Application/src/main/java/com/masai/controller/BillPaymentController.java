package com.masai.controller;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.DTO.BillPaymentDTO;
import com.masai.DTO.BillPaymentGetDTO;
import com.masai.entity.BillPayment;
import com.masai.entity.Customer;
import com.masai.entity.UserSession;

import com.masai.globalExceptionHandler.CustomerNotFoundException;
import com.masai.repository.CustomerDao;
import com.masai.repository.UserSessionDao;
import com.masai.repository.WalletDaoJpa;
import com.masai.service.BillPaymentServices;




@RestController
public class BillPaymentController {
	
	@Autowired
	private BillPaymentServices billservices;
	
	@Autowired
	private UserSessionDao userDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private WalletDaoJpa wDao;
	
	@PostMapping("/billpayment")
	public ResponseEntity<BillPaymentDTO> payBill(@Valid @RequestBody BillPaymentGetDTO billPayment, @RequestParam("key") String key) {
		
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
		BillPayment b=new BillPayment(billPayment,wDao.getById(user.getMobile()));
		return new ResponseEntity<BillPaymentDTO>(new  BillPaymentDTO(billservices.payBillPayment(b,user.getMobile())),HttpStatus.ACCEPTED);
		
	}
	

		@GetMapping("/bills")
		public List<BillPaymentDTO> getAllBillPayment(@RequestParam("key") String key){
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
			
			return billservices.viewBillPayment(wDao.getById(user.getMobile()));
		}

	

}
