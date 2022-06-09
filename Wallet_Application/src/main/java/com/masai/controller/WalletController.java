package com.masai.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.security.auth.login.AccountException;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.DTO.AccountDTO;
import com.masai.entity.Account;
import com.masai.entity.Customer;
import com.masai.entity.UserSession;
import com.masai.entity.Wallet;
import com.masai.globalExceptionHandler.CustomerNotFoundException;
import com.masai.repository.UserSessionDao;
import com.masai.repository.WalletDaoJpa;
import com.masai.service.AccountService;
import com.masai.service.CustomerServiceImpl;
import com.masai.service.LoginServiceImpl;

@RestController
public class WalletController {
	@Autowired
	private CustomerServiceImpl csi;
	@Autowired
	private LoginServiceImpl login;
	@Autowired
	private UserSessionDao userDao;
	@Autowired
	private AccountService aAccount;
	@Autowired
	private WalletDaoJpa wDao;
	
	
	@GetMapping("ViewAccount")
	public AccountDTO getAccount(@RequestParam("key") String key) throws AccountException{
		UserSession user=userDao.findByUuid(key);
		if(user==null) {
			throw new CustomerNotFoundException("You are not authoraised person please login first.");
		}
		LocalDateTime prev=user.getDateTime();
		LocalDateTime date=LocalDateTime.now();
		if (prev.getDayOfMonth() != date.getDayOfMonth()) {
			userDao.delete(user);
			throw new CustomerNotFoundException("Your session is expired please login again");
		}
		String number=user.getMobile();
		Customer c=csi.findBymobileNumber(number);
		Wallet w=wDao.getById(c.getMobileNumber());
		if(w.getBank()==null)
			throw new CustomerNotFoundException("No account Available this wallet no "+w.getNumber());
		return aAccount.getAccountByAccountNo(w.getBank(),w);
	}
	
	@GetMapping("ShowBalance")
	public String showBalance(@RequestParam("key") String key) throws AccountException{
		UserSession user=userDao.findByUuid(key);
		if(user==null) {
			throw new CustomerNotFoundException("You are not authoraised person please login first.");
		}
		LocalDateTime prev=user.getDateTime();
		LocalDateTime date=LocalDateTime.now();
		if (prev.getDayOfMonth() != date.getDayOfMonth()) {
			userDao.delete(user);
			throw new CustomerNotFoundException("Your session is expired please login again");
		}
		String number=user.getMobile();
		Customer c=csi.findBymobileNumber(number);
		Wallet w=wDao.getById(c.getMobileNumber());
		if(w.getBank()==null)
			throw new CustomerNotFoundException("No account Available this wallet no "+w.getNumber());
		return "Your wallet balance "+w.getBalance();
	}
	
	@GetMapping("ShowAccount")
	public AccountDTO showAccountBalance(@RequestParam("key") String key) throws AccountException{
		UserSession user=userDao.findByUuid(key);
		if(user==null) {
			throw new CustomerNotFoundException("You are not authoraised person please login first.");
		}
		LocalDateTime prev=user.getDateTime();
		LocalDateTime date=LocalDateTime.now();
		if (prev.getDayOfMonth() != date.getDayOfMonth()) {
			userDao.delete(user);
			throw new CustomerNotFoundException("Your session is expired please login again");
		}
		String number=user.getMobile();
		Customer c=csi.findBymobileNumber(number);
		Wallet w=wDao.getById(c.getMobileNumber());
		if(w.getBank()==null ||w.getBank()==null)
			throw new CustomerNotFoundException("No account Available this wallet no "+w.getNumber());
		return aAccount.getAccountByAccountNo(w.getBank(),w);
	}
	
	
	@GetMapping("depositAmount/{accountNo}/{money}")
	public AccountDTO debitMoneyFromAccount(@RequestParam("key") String key,@Valid@PathVariable("accountNo")
	@Pattern(regexp = "^[0-9]{6,20}",message="Invalid Account Number Foramateaa")
	String accountNo,@PathVariable("money") double money) throws AccountException{
		UserSession user=userDao.findByUuid(key);
		if(user==null) {
			throw new CustomerNotFoundException("You are not authoraised person please login first.");
		}
		LocalDateTime prev=user.getDateTime();
		LocalDateTime date=LocalDateTime.now();
		if (prev.getDayOfMonth() != date.getDayOfMonth()) {
			userDao.delete(user);
			throw new CustomerNotFoundException("Your session is expired please login again");
		}
		String number=user.getMobile();
		Customer c=csi.findBymobileNumber(number);
		Wallet w=wDao.getById(c.getMobileNumber());
		return aAccount.removeMoneyFromAccount(accountNo, w, money);
	}
	@GetMapping("fundTransferAccountToAccount/{accountNo1}/{accountNo2}/{money}")
	public AccountDTO sendMoneyInAccount(@RequestParam("key") String key,@Valid@PathVariable("accountNo1")
	@Pattern(regexp = "^[0-9]{6,20}",message="Invalid Account Number Foramateaa")
	String accountNo1,@Valid@PathVariable("accountNo2")
	@Pattern(regexp = "^[0-9]{6,20}",message="Invalid Account Number Foramateaa")String accountNo2,@PathVariable("money") double money) throws AccountException{
		if(accountNo1==accountNo2)
			throw new CustomerNotFoundException("can not send same account");
		UserSession user=userDao.findByUuid(key);
		if(user==null) {
			throw new CustomerNotFoundException("You are not authoraised person please login first.");
		}
		LocalDateTime prev=user.getDateTime();
		LocalDateTime date=LocalDateTime.now();
		if (prev.getDayOfMonth() != date.getDayOfMonth()) {
			userDao.delete(user);
			throw new CustomerNotFoundException("Your session is expired please login again");
		}
		String number=user.getMobile();
		Customer c=csi.findBymobileNumber(number);
		Wallet w=wDao.getById(c.getMobileNumber());
		
		return aAccount.sendMoneyInAccount(accountNo1, accountNo2, w, money);
	}
	
	
	
	
	@GetMapping("fundTransferAccountToMobile/{accountNo}/{mobileNo}/{money}")
	public AccountDTO sendToMobile(@RequestParam("key") String key,@Valid@PathVariable("accountNo")
	@Pattern(regexp = "^[0-9]{6,20}",message="Invalid Account Number Foramateaa")
	String accountNo,@PathVariable("mobileNo") String mobileNo,@PathVariable("money") double money) throws AccountException{
		UserSession user=userDao.findByUuid(key);
		if(user==null) {
			throw new CustomerNotFoundException("You are not authoraised person please login first.");
		}
		LocalDateTime prev=user.getDateTime();
		LocalDateTime date=LocalDateTime.now();
		if (prev.getDayOfMonth() != date.getDayOfMonth()) {
			userDao.delete(user);
			throw new CustomerNotFoundException("Your session is expired please login again");
		}
		
//		return aAccount.removeMoneyFromAccountAndAddOtherWallet(accountNo,wDao.getById(c1.getMobileNumber()),wDao.getById(c2.getMobileNumber()), money);
		return aAccount.sendMoneyInAccount(accountNo,wDao.getById(mobileNo).getBank() , wDao.getById(user.getMobile()), money);
	}
	
	@GetMapping("fundTransferMobileToMobile/{mobileNo}/{money}")
	public AccountDTO sendMoneyToMobile(@RequestParam("key") String key,@Valid@PathVariable("mobileNo")
	@Pattern(regexp = "^[0-9]{10}",message="Invalid Mobile Number Foramateaa")String mobile,@PathVariable("mobileNo") String mobileNo,@PathVariable("money") double money) throws AccountException{
		UserSession user=userDao.findByUuid(key);
		if(user==null) {
			throw new CustomerNotFoundException("You are not authoraised person please login first.");
		}
		LocalDateTime prev=user.getDateTime();
		LocalDateTime date=LocalDateTime.now();
		if (prev.getDayOfMonth() != date.getDayOfMonth()) {
			userDao.delete(user);
			throw new CustomerNotFoundException("Your session is expired please login again");
		}
		Wallet w=wDao.getById(user.getMobile());
//		return aAccount.sendMoneyUsingMobileNo(wDao.getById(c1.getMobileNumber()),wDao.getById(c2.getMobileNumber()), money);
		return aAccount.sendMoneyInAccount(w.getBank(), wDao.getById(mobileNo).getBank(), w, money);
	}
	
	@GetMapping("updateAccount/{accountNo}")
	public Wallet changeAccount(@RequestParam("key") String key,@Valid@PathVariable("accountNo")
	@Pattern(regexp = "^[0-9]{6,20}",message="Invalid Account Number Foramateaa")
	String account) throws AccountException{
		UserSession user=userDao.findByUuid(key);
		if(user==null) {
			throw new CustomerNotFoundException("You are not authoraised person please login first.");
		}
		LocalDateTime prev=user.getDateTime();
		LocalDateTime date=LocalDateTime.now();
		if (prev.getDayOfMonth() != date.getDayOfMonth()) {
			userDao.delete(user);
			throw new CustomerNotFoundException("Your session is expired please login again");
		}
		Wallet w=wDao.getById(user.getMobile());
		return aAccount.updateAccount(account, w);
//		return null;
	}
}
