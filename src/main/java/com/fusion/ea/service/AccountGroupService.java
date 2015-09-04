package com.fusion.ea.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fusion.ea.entity.AccountGroup;
import com.fusion.ea.entity.File;
import com.fusion.ea.entity.User;
import com.fusion.ea.repository.AccountGroupRepository;

@Service
public class AccountGroupService {
	
	@Autowired
	private AccountGroupRepository accGroupRepository;
	
	@Autowired
	private SessionService sessionService;
	
	public AccountGroup findById(Integer id) throws Exception {
		return accGroupRepository.findOne(id);
	}
	
	public List<AccountGroup> findAll() throws Exception {
		File file = sessionService.getSelectedFile();
		return accGroupRepository.findByFileAndDeleted(file, false);
	}
	
	public AccountGroup save(AccountGroup group) throws Exception {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
		group.setCreatedAt(new Date());
		group.setCreatedBy(user);
		group.setFile(sessionService.getSelectedFile());
		return accGroupRepository.save(group);
	}
	
	public AccountGroup update(AccountGroup group) throws Exception {
		// TODO Auto-generated method stub
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
		group.setModifiedAt(new Date());
		group.setModifiedBy(user);
		return accGroupRepository.save(group);
	}
	
	public void delete(AccountGroup group) throws Exception {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
		group.setDeletedAt(new Date());
		group.setDeletedBy(user);
		group.setDeleted(true);
		accGroupRepository.save(group);
	}

	public AccountGroup findByDescription(String description) throws Exception {
		// TODO Auto-generated method stub
		File file = sessionService.getSelectedFile();
		return accGroupRepository.findByDescriptionAndFileAndDeleted(description, file, false);
	}

}
