package com.masai.repository;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.masai.DTO.SavedContactDTO;
import com.masai.entity.Account;
import com.masai.entity.SavedContact;
import com.masai.entity.Wallet;
@Repository
public interface SavedContactDao extends JpaRepository<SavedContact, Integer>{

	
	public List<SavedContactDTO> findAllBywallet(Wallet walletId);
	
	public List<SavedContact> findBywallet(Wallet walletId);
	
	public SavedContactDTO findByWalletAndMobileNo(String mobileNo,Wallet wallet);
	
	public SavedContact deleteByWalletAndMobileNo(String mobileNo,Wallet wallet);
	
	
//	@Query("select new com.masai.DTO.BeneficiaryDTO(b.id, b.mobileNo, b.name)"
//	  		+ " from BeneficiaryDetails b where b.mobileNo=?1 AND b.wallet.id=?2")
//	public SavedContactDTO findByWalletId(String mobile,Integer id);
//	List<SavedContact> findAllByWalletId(int a);
//	SavedContact findBy(String string);
//	SavedContact findByMobileNoAndWallet(String phone, Wallet wallet);
//	
//	public SavedContact findByMobileNoAndWalletId(String mobile,Integer id);
	
	
}