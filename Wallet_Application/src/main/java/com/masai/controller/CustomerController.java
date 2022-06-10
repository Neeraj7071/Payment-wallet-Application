package com.masai.controller;


import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.DTO.CustomerDTO;
import com.masai.DTO.LoginDTO;
import com.masai.entity.Customer;
import com.masai.entity.UserSession;
import com.masai.entity.Wallet;
import com.masai.globalExceptionHandler.CustomerNotFoundException;
import com.masai.repository.UserSessionDao;
import com.masai.service.CustomerServiceImpl;
import com.masai.service.LoginServiceImpl;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerServiceImpl csi;
	@Autowired
	private LoginServiceImpl login;
	@Autowired
	private UserSessionDao userDao;
	
//	@GetMapping("/get")
//	public String getDeta() {
//		return "Hello buddy this is testing";
//	}
	@PostMapping("userlogin")
	public ResponseEntity<UserSession> loginSession(@Valid @RequestBody LoginDTO cusD){
		
		return new ResponseEntity<>(login.loginService(cusD),HttpStatus.OK);
	}
	@GetMapping("userlogout")
	public ResponseEntity<String> logoutSession(@RequestParam("key") String key){
		return new ResponseEntity<>(login.logoutService(key),HttpStatus.OK);
	}
	
	@PostMapping("/customers")
	public ResponseEntity<Customer> createAccount(@Valid @RequestBody CustomerDTO cs){
		return new ResponseEntity<>(csi.createAcc(cs),HttpStatus.CREATED);
	}
	
	@GetMapping("/customers/showblance")
	public ResponseEntity<Wallet> showBlance(@RequestParam("key") String key){
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
		return new ResponseEntity<>(csi.showBlacnce(user.getMobile()), HttpStatus.OK);
	}

	
	@GetMapping("customers")
	public ResponseEntity<Customer> getCustomer(@RequestParam("key") String key){
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
		
		return new ResponseEntity<>(csi.getListCustomer(key),HttpStatus.OK);
	}
	@PatchMapping("/customersupdate")
	public ResponseEntity<Customer> updateAccount(@Valid@RequestBody CustomerDTO cs,@RequestParam("key") String key){
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
		Customer ucs=csi.updateCustomer(cs,user.getMobile());
//		System.out.println(ucs);
		return new ResponseEntity<> (ucs,HttpStatus.OK);
	}
	
//	@PostMapping("userlogin")
//	public ResponseEntity<UserSession> loginSession(@Valid @RequestBody LoginDTO cusD){
//		
//		return new ResponseEntity<>(login.loginService(cusD),HttpStatus.OK);
//	}
}
