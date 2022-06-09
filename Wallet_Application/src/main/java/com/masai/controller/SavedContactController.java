package com.masai.controller;

import java.time.LocalDateTime;
import java.util.List;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.DTO.SavedContactDTO;
import com.masai.entity.SavedContact;
import com.masai.entity.Customer;
import com.masai.entity.UserSession;
import com.masai.entity.Wallet;
import com.masai.globalExceptionHandler.CustomerNotFoundException;
import com.masai.repository.SavedContactDao;
import com.masai.repository.CustomerDao;
import com.masai.repository.UserSessionDao;
import com.masai.repository.WalletDaoJpa;
import com.masai.service.SavedContactService;


@RestController
public class SavedContactController {
	
	@Autowired
	private SavedContactService savedContactService;
	@Autowired
	private SavedContactDao beneficiaryDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private UserSessionDao userDao;
	@Autowired
	private WalletDaoJpa wDao;
	
	
	@PostMapping(value="/addContant")
	public SavedContactDTO addContantRest(@RequestParam String mobile, @RequestParam("key") String key) throws CustomerNotFoundException {
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
		
		Optional<Customer> custo=customerDao.findById(user.getMobile());
		Optional<Customer> custo2=customerDao.findById(mobile);
		Wallet w=wDao.getById(custo2.get().getMobileNumber());
		if(!custo2.isPresent())
			throw new CustomerNotFoundException("This Customer detail not found in our database");
		if(custo.get().getMobileNumber()==custo2.get().getMobileNumber())
			throw new CustomerNotFoundException("You cann't insert same mobile number in beneficiary details");
	
		if(beneficiaryDao.findByWalletAndMobileNo(mobile,w)!=null)
			throw new CustomerNotFoundException("This beneficiary already exits to your account");
		
		return  savedContactService.addBeneficiary(wDao.getById(custo.get().getMobileNumber()), mobile,custo2.get().getName());
	}
	
	@DeleteMapping(value="/savedContant")
	public SavedContact deleteContantRest(@RequestParam("phone") String phone, @RequestParam("key") String key) throws CustomerNotFoundException {
		
		
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
		
		Optional<Customer> cus=customerDao.findById(user.getMobile());
		Wallet w=wDao.getById(cus.get().getMobileNumber());
		return  savedContactService.deleteBeneficiary(w,phone);
	}

	
	
	@GetMapping(value="/savedContant/view/")
	public SavedContactDTO viewContactRest(@RequestParam String mobileNumber) throws CustomerNotFoundException {
		
		Optional<Customer> c=customerDao.findById(mobileNumber);
		if(c.isPresent()) {
			return savedContactService.viewBeneficiary(mobileNumber);
		}
		throw new CustomerNotFoundException("Invalid Number");
		
	}
	
	
	@PostMapping(value="/ViewAllSavedContant")
		public List<SavedContact> viewAllsavedContant(@RequestParam("key") String key) throws CustomerNotFoundException {
		// TODO Auto-generated method stub
		
		
		
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

		
		Optional<Customer> cusopt=customerDao.findById(user.getMobile());
		Customer customer=cusopt.get();
		
			return savedContactService.viewAllBeneficiary(wDao.getById(customer.getMobileNumber()));
		
	}
	
}
