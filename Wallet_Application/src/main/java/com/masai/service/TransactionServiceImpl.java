package com.masai.service;

import com.masai.DTO.Date;
import com.masai.DTO.TransactionDTO;
import com.masai.entity.Wallet;
import com.masai.globalExceptionHandler.CustomerNotFoundException;
import com.masai.repository.CustomerDao;
import com.masai.repository.TransactionDao;
import com.masai.repository.UserSessionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionServiceIntr {

    @Autowired
    private TransactionDao tDao;

    @Override
    public List<TransactionDTO> getTransaction(Wallet w) throws CustomerNotFoundException {

        List<TransactionDTO> tran=tDao.findByWallet(w);
        
        return tran;
    }
    @Override
    public List<TransactionDTO> getTransactionByDate(Date date, Wallet w) throws CustomerNotFoundException {
    	 
    	List<TransactionDTO> tran=tDao.findByWallet(w);
    	
    	List<TransactionDTO> upend=new ArrayList<>();
  
    	
    	for(TransactionDTO tr:tran) {
    		if(tr.getDateTime().getYear()>=date.getDateFrom().getYear() &&
    				tr.getDateTime().getMonthValue()>=date.getDateFrom().getMonthValue() 
    				&& tr.getDateTime().getDayOfMonth()>=date.getDateFrom().getDayOfMonth() &&
    				tr.getDateTime().getYear()<=date.getDateTo().getYear() &&
    				tr.getDateTime().getMonthValue()<=date.getDateTo().getMonthValue() 
    				&& tr.getDateTime().getDayOfMonth()<=date.getDateTo().getDayOfMonth()) {
    			
    			upend.add(tr);
    			
    		}
    		
    	}
    	
    	
        return upend;
    }
	@Override
	public List<TransactionDTO> getTransactionByType(String type, Wallet w) throws CustomerNotFoundException {
		List<TransactionDTO> tran=tDao.findByWallet(w);
    	
    	List<TransactionDTO> upend=new ArrayList<>();
  
    	
    	for(TransactionDTO tr:tran) {
    		if(tr.getTransactionType().equalsIgnoreCase(type)) {
    			
    			upend.add(tr);
    			
    		}
    		
    	}
    	
    	
        return upend;
	}
}