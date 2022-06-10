package com.masai.service;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.DTO.CustomerDTO;
import com.masai.entity.Customer;
import com.masai.entity.UserSession;
import com.masai.entity.Wallet;
import com.masai.globalExceptionHandler.CustomerNotFoundException;
import com.masai.repository.CustomerDao;
import com.masai.repository.UserSessionDao;
import com.masai.repository.WalletDaoJpa;

@Service
public class CustomerServiceImpl implements customerServiceIntr{
	
	@Autowired
	private CustomerDao wdo;
	@Autowired
	private UserSessionDao user;
	@Autowired
	private WalletDaoJpa walletDao;
	
	@Override
	public Wallet showBlacnce(String mobile) throws CustomerNotFoundException {
//			Optional<Customer> cust= wdo.findById(mobile);
//		return wdo.viewBalance(cust.get().getWallet().getId(),cust.get().getWallet().getId());
			Optional<Wallet> w=walletDao.findById(mobile);
			return w.get();
		//return wdo.findById(mobile).orElseThrow(()->new );
	}


	


	@Override
	public Customer getListCustomer(String key) throws CustomerNotFoundException {
		UserSession userS=user.findByUuid(key);	
		
		return wdo.findById(userS.getMobile()).get();
	}


	@Override
	public Customer createAcc(CustomerDTO cs)throws CustomerNotFoundException  {
		Optional<Customer> opt=wdo.findById(cs.getMobileNo());
		
		if(opt.isPresent())
			throw new CustomerNotFoundException("Customer is present already with this mobile number : "+cs.getMobileNo());
		Wallet wa=new Wallet(cs.getMobileNo(),(double) 0,null);
		walletDao.save(wa);
		return wdo.save(new Customer(cs,wa));
	}





	@Override
	public Customer updateCustomer(CustomerDTO customer, String num) throws CustomerNotFoundException {
		if(customer.getMobileNo().equals(num)) {
			wdo.save(new Customer(customer,walletDao.getById(num)));			
			Optional<Customer> opt=wdo.findById(customer.getMobileNo());
			return opt.get();
		}
//		if(opt.isPresent()==false) {
//			wdo.delete(wdo.getById(num));
//			walletDao.findById(customer.getMobileNo());
//			Wallet w=walletDao.findById(num).get();
//			walletDao.delete(w);
//			w.setNumber(customer.getMobileNo());
//			walletDao.save(w);
//			return wdo.save(new Customer(customer,w));
//		}
//		else
		throw new CustomerNotFoundException("Customer Mobile number could not be change");
//			throw new CustomerNotFoundException("Customer already present");
	}





	@Override
	public Customer findBymobileNumber(String number) {
		return wdo.findById(number).get();
	}




}
