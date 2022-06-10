package com.masai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.DTO.SavedContactDTO;
import com.masai.entity.SavedContact;
import com.masai.entity.Customer;
import com.masai.entity.Wallet;
import com.masai.globalExceptionHandler.CustomerNotFoundException;
import com.masai.repository.SavedContactDao;
import com.masai.repository.CustomerDao;
import com.masai.repository.WalletDaoJpa;



@Service
public class SavedContactService implements SavedContactServiceInter {
	@Autowired
	private SavedContactDao beneficiaryDao;
	
	@Autowired
	private WalletDaoJpa wDao;
	
	@Autowired
	private CustomerDao cDao;
	@Override
	public SavedContactDTO addBeneficiary(Wallet wallet, String num, String name) throws CustomerNotFoundException {
		SavedContact bene=new SavedContact();
		bene.setMobileNo(num);
		bene.setName(name);
		bene.setWallet(wallet);
		List<SavedContactDTO> list=beneficiaryDao.findAllBywallet(wallet);
		for(SavedContactDTO s:list) {
//			System.out.println(s+num);
			if(s.getMobileNo().equals(num))
				throw new CustomerNotFoundException("user already present");
		}
		return new SavedContactDTO(beneficiaryDao.save(bene));
		
	}

	@Override
	public SavedContact deleteBeneficiary(Wallet wallet, String num) throws CustomerNotFoundException {
		List<SavedContact> list=beneficiaryDao.findBywallet(wallet);
		for(SavedContact s:list) {
			System.out.println(s+num);
			if(s.getMobileNo().equals(num)) {
				beneficiaryDao.delete(s);
				return s;
			}
		}
		throw new CustomerNotFoundException("Invalid Input");
		
	}

	@Override
	public SavedContactDTO viewBeneficiary(String num) throws CustomerNotFoundException {
		Wallet w=wDao.getById(num);
		if(w!=null) {
			return cDao.findByMobileNumber(num);
		}
		throw new CustomerNotFoundException("Invalid Input");
	}

	@Override
	public List<SavedContactDTO> viewAllBeneficiary(Customer c) throws CustomerNotFoundException {
		
		return beneficiaryDao.findAllBywallet(c.getWallet());
	}

	
	
	
	
	

//	@Override
//	public SavedContactDTO addBeneficiary(Customer cust,Wallet wallet) throws CustomerNotFoundException {
//		// TODO Auto-generated method stub
//		SavedContact bene=new SavedContact();
//		
//		bene.setMobileNo(cust.getMobileNumber());
//		bene.setName(cust.getName());
//		bene.setWallet(wallet);
//		beneficiaryDao.save(bene);
//		
//		return beneficiaryDao.findByWalletId(cust.getMobileNumber(),wallet.getId());
//	}
//
//	@Override
//	public SavedContact deleteBeneficiary(String phone, Wallet wallet) throws CustomerNotFoundException {
//		// TODO Auto-generated method stub
//		//
//		
//		Optional<SavedContact> optBene2=Optional.ofNullable(beneficiaryDao.findByMobileNoAndWallet(phone,wallet));
//		
//		SavedContact bn;
//		
//		if(optBene2.isPresent()) {
//			bn=optBene2.get();
//			bn.setWallet(null);;
//			beneficiaryDao.delete(bn);
//			return bn;
//		}
//		
//		else throw new CustomerNotFoundException("Invalid Input");
//	}
//
//	@Override
//	public SavedContact viewBeneficiary(String mobileNumber, Wallet wallet) throws CustomerNotFoundException {
//		if(mobileNumber!=null&&mobileNumber!="") {
//			return beneficiaryDao.findByWallet(wallet);
//		}
//		throw new CustomerNotFoundException("Invalid Input");
//		
//	}

//	@Override
//	public List<SavedContact> viewAllBeneficiary(Wallet wallet) throws CustomerNotFoundException {
//		// TODO Auto-generated method stub
//		if(wallet!=null) {
//			String a=wallet.getNumber();
//			return beneficiaryDao.findAllBywallet(wallet);
//		}
//		throw new CustomerNotFoundException("Invalid Input");
//	}

	
}
