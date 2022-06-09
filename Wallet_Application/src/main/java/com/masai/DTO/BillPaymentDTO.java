package com.masai.DTO;

import java.time.LocalDateTime;

import com.masai.entity.BillPayment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Data
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class BillPaymentDTO {
	
	

	private Integer billId;
	private String billType;
	private Double amount;
	private LocalDateTime paymentDate;
	
	public BillPaymentDTO(BillPayment b) {
		super();
		this.billId = b.getBillId();
		this.billType = b.getBillType();
		this.amount = b.getAmount();
		this.paymentDate = b.getPaymentDate();
	}
	
	
	
}
