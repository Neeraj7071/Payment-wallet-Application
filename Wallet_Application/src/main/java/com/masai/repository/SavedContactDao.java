package com.masai.repository;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.DTO.SavedContactDTO;
import com.masai.entity.Account;
import com.masai.entity.SavedContact;
import com.masai.entity.Wallet;
@Repository
public interface SavedContactDao extends JpaRepository<SavedContact, Integer>{

	
	public List<SavedContact> findAllBywallet(Wallet walletId);
	
	public SavedContactDTO findByWalletAndMobileNo(String num,Wallet wallet);
	
	public SavedContact deleteByWalletAndMobileNo(String num,Wallet wallet);
//	List<SavedContact> findAllByWalletId(int a);
//	SavedContact findBy(String string);
//	SavedContact findByMobileNoAndWallet(String phone, Wallet wallet);
//	
//	public SavedContact findByMobileNoAndWalletId(String mobile,Integer id);
//	
//	  @Query("select new com.masai.DTO.SavedContactDTO(b.id, b.mobileNo, b.name)"
//		  		+ " from SavedContact b where b.mobileNo=?1 AND b.wallet.id=?2")
//	public SavedContactDTO findByWalletId(String mobile,Integer id);
}