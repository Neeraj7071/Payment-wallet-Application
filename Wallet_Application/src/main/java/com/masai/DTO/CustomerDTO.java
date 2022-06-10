package com.masai.DTO;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDTO {
	
	@Pattern(regexp = "^[0-9]{10}",message="Invalid Mobile Number Foramate")
	private String mobileNo;
	@Pattern(regexp = "^[a-zA-Z]{1,30}",message="Invalid name Foramate")
	private String name;
	@Size(min = 8, message="password minimum length 8 h")
	private String password;
	
}
