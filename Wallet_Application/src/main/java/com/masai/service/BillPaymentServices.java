package com.masai.service;

import java.util.List;

import com.masai.entity.BillPayment;
import com.masai.entity.Wallet;


public interface BillPaymentServices {

	public BillPayment payBillPayment(BillPayment billPayment, String mob);
	public List<BillPayment> viewBillPayment(Wallet w);
	
}

