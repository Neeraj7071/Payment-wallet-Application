package com.masai.DTO;

import com.masai.entity.Customer;
import com.masai.entity.SavedContact;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SavedContactDTO {
	
	private String mobileNo;
	private String name;
	/**
	 * @param mobileNo
	 * @param name
	 */
	public SavedContactDTO(SavedContact cc) {
		super();
		this.mobileNo = cc.getMobileNo();
		this.name = cc.getName();
	}
	public SavedContactDTO(Customer cc) {
		super();
		this.mobileNo = cc.getMobileNumber();
		this.name = cc.getName();
	}

}