package com.fusion.ea.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fusion.ea.entity.File;
import com.fusion.ea.entity.Daybook;
import com.fusion.ea.entity.User;
import com.fusion.ea.repository.DaybookRepository;

@Service
public class DaybookService {

	@Autowired
	private DaybookRepository daybookRepository;
	
	@Autowired
	private SessionService sessionService;
	
	public Daybook findById(Integer id) throws Exception {
		return daybookRepository.findOne(id);
	}
	
	public List<Daybook> findAll() throws Exception {
		File file = sessionService.getSelectedFile();
		return daybookRepository.findByFileAndDeleted(file, false);
	}
	
	public Daybook save(Daybook daybook) throws Exception {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
		daybook.setCreatedAt(new Date());
		daybook.setCreatedBy(user);
		daybook.setFile(sessionService.getSelectedFile());
		return daybookRepository.save(daybook);
	}
	
	public Daybook update(Daybook daybook) throws Exception {
		// TODO Auto-generated method stub
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
		daybook.setModifiedAt(new Date());
		daybook.setModifiedBy(user);
		return daybookRepository.save(daybook);
	}
	
	public void delete(Daybook daybook) throws Exception {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
		daybook.setDeletedAt(new Date());
		daybook.setDeletedBy(user);
		daybook.setDeleted(true);
		daybookRepository.save(daybook);
	}

	public Daybook findByDescription(String description) throws Exception {
		// TODO Auto-generated method stub
		File file = sessionService.getSelectedFile();
		return daybookRepository.findByNameAndFileAndDeleted(description, file, false);
	}
	
}
