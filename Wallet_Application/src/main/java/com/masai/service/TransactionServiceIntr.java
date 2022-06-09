package com.masai.service;

import com.masai.DTO.Date;
import com.masai.DTO.TransactionDTO;
import com.masai.entity.Transaction;
import com.masai.entity.Wallet;
import com.masai.globalExceptionHandler.CustomerNotFoundException;
import java.util.List;

public interface TransactionServiceIntr {

    public List<TransactionDTO> getTransaction(Wallet w) throws CustomerNotFoundException;

    public List<TransactionDTO> getTransactionByDate(Date date,Wallet w)throws CustomerNotFoundException;

    public List<TransactionDTO> getTransactionByType(String type,Wallet w) throws CustomerNotFoundException;
}
