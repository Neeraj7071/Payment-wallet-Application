package com.masai.service;

import java.util.List;

import com.masai.DTO.SavedContactDTO;
import com.masai.entity.Customer;
import com.masai.entity.SavedContact;
import com.masai.entity.Wallet;
import com.masai.globalExceptionHandler.CustomerNotFoundException;

public interface SavedContactServiceInter {
	
	public SavedContactDTO addBeneficiary(Wallet wallet,String name,String num) throws CustomerNotFoundException;
	
	public SavedContact deleteBeneficiary(Wallet wallet,String num) throws CustomerNotFoundException;
	
	public SavedContactDTO viewBeneficiary(String num) throws CustomerNotFoundException;
	
	public List<SavedContactDTO> viewAllBeneficiary(Customer c) throws CustomerNotFoundException;
	
}
