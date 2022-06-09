package com.masai.service;

import com.masai.DTO.CustomerDTO;
import com.masai.entity.Customer;
import com.masai.entity.Wallet;
import com.masai.globalExceptionHandler.CustomerNotFoundException;

public interface customerServiceIntr {
	
	public Customer findBymobileNumber(String number);
	
	public Customer createAcc(CustomerDTO cs)throws CustomerNotFoundException;
	
	public Wallet showBlacnce(String mobile)throws CustomerNotFoundException;
	
	public Customer getListCustomer(String key) throws CustomerNotFoundException ;
	
	public Customer updateCustomer(CustomerDTO customer,String num) throws CustomerNotFoundException;

}
