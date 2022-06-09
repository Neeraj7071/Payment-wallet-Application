package com.masai.service;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	public Customer createAcc(Customer cs)throws CustomerNotFoundException  {
		Optional<Customer> opt=wdo.findById(cs.getMobileNumber());
		
		if(opt.isPresent())
			throw new CustomerNotFoundException("Customer is present already with this mobile number : "+cs.getMobileNumber());
		Wallet wa=new Wallet(cs.getMobileNumber(),(double) 0,null);
		walletDao.save(wa);
		return wdo.save(cs);
	}





	@Override
	public Customer updateCustomer(Customer customer, String num) throws CustomerNotFoundException {
		if(customer.getMobileNumber().equals(num))
			return wdo.save(customer);
		
		Optional<Customer> opt=wdo.findById(customer.getMobileNumber());
		if(opt.isPresent()==false) {
			wdo.delete(wdo.getById(num));
			walletDao.findById(customer.getMobileNumber());
			Wallet w=walletDao.findById(num).get();
			walletDao.delete(w);
			w.setNumber(customer.getMobileNumber());
			walletDao.save(w);
			return wdo.save(customer);
		}
		else
			throw new CustomerNotFoundException("Customer already present");
	}





	@Override
	public Customer findBymobileNumber(String number) {
		return wdo.findById(number).get();
	}










	


}
