package com.masai.service;

import com.masai.entity.Customer;
import com.masai.entity.Wallet;
import com.masai.globalExceptionHandler.CustomerNotFoundException;

public interface customerServiceIntr {
	
	public Customer findBymobileNumber(String number);
	
	public Customer createAcc(Customer cs)throws CustomerNotFoundException;
	
	public Wallet showBlacnce(String mobile)throws CustomerNotFoundException;
	
//	public String fundTransfer(String mobileNo,String targetMobileNo, Double amount)throws CustomerNotFoundException;
	
	public Customer getListCustomer(String key) throws CustomerNotFoundException ;
	
	public Customer updateCustomer(Customer customer,String num) throws CustomerNotFoundException;

}
