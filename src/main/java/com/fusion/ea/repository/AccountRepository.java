package com.fusion.ea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusion.ea.entity.Account;
import com.fusion.ea.entity.File;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	public List<Account> findByFile(File file);
	
	public List<Account> findByFileAndDeleted(File file, boolean deleted);

	public Account findByNameAndFileAndDeleted(String name, File file, boolean b);

	public List<Account> findByFileAndDeletedAndCashBank(File file, boolean b,
			boolean c);
	
}
