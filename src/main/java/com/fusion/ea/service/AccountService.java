package com.fusion.ea.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fusion.ea.entity.Account;
import com.fusion.ea.entity.File;
import com.fusion.ea.entity.User;
import com.fusion.ea.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accRepository;
	
	@Autowired
	private SessionService sessionService;
	
	public Account findOne(int id) throws Exception {
		return accRepository.findOne(id);
	}
	
	public List<Account> findAll() throws Exception {
		File file = sessionService.getSelectedFile();
		return accRepository.findByFileAndDeleted(file,false);
	}

	public Account save(Account account) throws Exception {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getDetails();
		account.setCreatedAt(new Date());
		account.setCreatedBy(user);
		account.setFile(sessionService.getSelectedFile());
		return accRepository.save(account);
	}
	
	public Account update(Account account) throws Exception {
		Account oldAccount = accRepository.findOne(account.getId());
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getDetails();
		oldAccount.setName(account.getName());
		oldAccount.setProfitLoss(account.isProfitLoss());
		oldAccount.setDebtor(account.isDebtor());
		oldAccount.setCashBank(account.isCashBank());
		oldAccount.setOpeningBalance(account.getOpeningBalance());
		oldAccount.setCredit(account.isCredit());
		oldAccount.setModifiedAt(new Date());
		oldAccount.setModifiedBy(user);
		return accRepository.save(oldAccount);
	}
	
	public Account updateForTag(Account account) throws Exception {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getDetails();
		account.setModifiedAt(new Date());
		account.setModifiedBy(user);
		return accRepository.save(account);
	}
	
	public void delete(Account account) throws Exception {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getDetails();
		account.setDeleted(true);
		account.setDeletedAt(new Date());
		account.setDeletedBy(user);
		accRepository.save(account);
	}

	public Account findByName(String name) throws Exception {
		// TODO Auto-generated method stub
		File file = sessionService.getSelectedFile();
		return accRepository.findByNameAndFileAndDeleted(name, file, false);
	}

	public List<Account> findCashBank() {
		File file = sessionService.getSelectedFile();
		return accRepository.findByFileAndDeletedAndCashBank(file,false,true);
	}
	
}
