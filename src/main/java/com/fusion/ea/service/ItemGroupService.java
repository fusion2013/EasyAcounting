package com.fusion.ea.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fusion.ea.entity.ItemGroup;
import com.fusion.ea.entity.File;
import com.fusion.ea.entity.User;
import com.fusion.ea.repository.ItemGroupRepository;

@Service
public class ItemGroupService {

	@Autowired
	private ItemGroupRepository accGroupRepository;
	
	@Autowired
	private SessionService sessionService;
	
	public ItemGroup findById(Integer id) throws Exception {
		return accGroupRepository.findOne(id);
	}
	
	public List<ItemGroup> findAll() throws Exception {
		File file = sessionService.getSelectedFile();
		return accGroupRepository.findByFileAndDeleted(file, false);
	}
	
	public ItemGroup save(ItemGroup group) throws Exception {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
		group.setCreatedAt(new Date());
		group.setCreatedBy(user);
		group.setFile(sessionService.getSelectedFile());
		return accGroupRepository.save(group);
	}
	
	public ItemGroup update(ItemGroup group) throws Exception {
		// TODO Auto-generated method stub
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
		group.setModifiedAt(new Date());
		group.setModifiedBy(user);
		return accGroupRepository.save(group);
	}
	
	public void delete(ItemGroup group) throws Exception {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
		group.setDeletedAt(new Date());
		group.setDeletedBy(user);
		group.setDeleted(true);
		accGroupRepository.save(group);
	}

	public ItemGroup findByDescription(String description) throws Exception {
		// TODO Auto-generated method stub
		File file = sessionService.getSelectedFile();
		return accGroupRepository.findByDescriptionAndFileAndDeleted(description, file, false);
	}
	
}
